import java.util.*;

class Main {
  // Clean the string of empty spaces and quotation marks 
  public static String trimEntry(StringBuilder sb) {
    String s = sb.toString().trim().replace("\"", "");
    return s;
  }

  // Insert cleaned key and value into map  
  public static void insertKeyValuePair(StringBuilder key, StringBuilder value, HashMap<String, Object> map) {
    String keyString = trimEntry(key);
    String valueString = trimEntry(value);
    map.put(keyString, valueString);
  }

  // Reset key/value stringbuilders 
  public static void resetKeyValuePair(StringBuilder key, StringBuilder value) {
    key.setLength(0);
    value.setLength(0);
  }

  public static HashMap<String, Object> parse(String input) {
    // Return empty map if not a valid input
    if (input.isEmpty() || input.length() == 0 || input.charAt(0) != '{') {
      return new HashMap<>();
    }
    
    Stack<HashMap<String, Object>> stack = new Stack<>();
    HashMap<String, Object> map = null;
    StringBuilder key = new StringBuilder();
    StringBuilder value = new StringBuilder();
    int left = 0;
    
    for (int right = 0; right < input.length(); right++) {
      char ch = input.charAt(right);
      // Start of new object
      if (ch == '{') {
        // Case where there is a nested JSON object; push current map into stack while nested map is created 
        if (map != null) {
          stack.push(map);
          resetKeyValuePair(key, value);
        }
        // Create the new map
        map = new HashMap<String, Object>();
        left = right + 1; 
      }
      // End of object
      else if (ch == '}') {
        // If our current object is a nested object, append current object to parent map
        if (!stack.isEmpty()) {
          // Set value string builder 
          value.append(input.substring(left, right));
          insertKeyValuePair(key, value, map);
          resetKeyValuePair(key, value);
          left = right + 1;
          HashMap<String, Object> parentMap = stack.pop();
          for (String parentKey : parentMap.keySet()) {
            if (parentMap.get(parentKey) == null) {
              parentMap.put(parentKey, map);
            }
          }
          // Set our current map back to the parent map 
          map = parentMap;
        }
        // If it is not a nested object and it is the last 'value' before the end of the object (values are found and inserted when a ',' is found)
        else if (input.charAt(left) != '}') {
          value.append(input.substring(left, right));
          insertKeyValuePair(key, value, map);
          resetKeyValuePair(key, value);
        }
      }

      // Traversed the 'value' part of the key-value pair
       else if (ch == ',') {
        value.append(input.substring(left, right));
        insertKeyValuePair(key, value, map);
        resetKeyValuePair(key, value);
        left = right + 1;
       }
      // Traversed the 'key' part of the key-value pair
       else if (ch == ':') {
        key.append(input.substring(left, right));
        String insertKey = trimEntry(key);
        map.put(insertKey, null);
        left = right + 1;
      }
    }
    return map;
  }

  public static void main(String[] args) {
    String json = "{"
        + " \"debug\" : \"on\","
        + " \"window\" : {"
        + " \"title\" : \"sample\","
        + " \"size\" : \"500\""
        + "}"
        + "}";
    HashMap<String, Object> res = parse(json);
    System.out.println(res); // Returns HashMap 
    System.out.println(res.get("debug")); // Returns 'on'
    System.out.println(res.get("window")); // Returns HashMap containing 'size' and 'sample'
    HashMap obj = (HashMap) res.get("window"); 

    System.out.println(obj.get("title")); // Returns 'sample'
    System.out.println(obj.get("size")); // Returns '500'

  }
}
