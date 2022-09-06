import java.util.*;

interface treeOperations {
  String serialize(Node root);
  Node deserialize(String str);
}

public class TreeSerializer implements treeOperations {
  private static final String NULL_SYMBOL = "X"; 
  private static final String DELIMITER = ",";
  
  public String serialize(Node root) {
    if (root == null) return NULL_SYMBOL + DELIMITER; 
    String leftSerialized = serialize(root.left);
    String rightSerialized = serialize(root.right);
    return root.num + DELIMITER + leftSerialized + rightSerialized;
  }

  public Node deserialize(String data) {
    Queue<String> nodesLeftToDeserialize = new LinkedList<>();
    nodesLeftToDeserialize.addAll(Arrays.asList(data.split(DELIMITER))); 
    return deserializeHelper(nodesLeftToDeserialize);
  }

  public Node deserializeHelper(Queue<String> nodesLeftToDeserialize) {
    String valueForNode = nodesLeftToDeserialize.poll();
    if (valueForNode.equals(NULL_SYMBOL)) return null; 

    Node newNode = new Node(Integer.valueOf(valueForNode));
    newNode.left = deserializeHelper(nodesLeftToDeserialize);
    newNode.right = deserializeHelper(nodesLeftToDeserialize);

    return newNode;
  }
}

