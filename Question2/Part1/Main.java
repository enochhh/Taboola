class Main {
    public static void main(String[] args) {
       TreeSerializer ser = new TreeSerializer();
      TreeSerializer deser = new TreeSerializer();
      Node newNode = new Node(5);
      newNode.left = new Node(6);
      newNode.left.left = new Node(25);
      newNode.right = new Node(72);
      newNode.right.right = new Node(34);
      String str = ser.serialize(newNode);
      System.out.println(str);
      Node ans = deser.deserialize(str);
      System.out.println(ans.num);
      System.out.println(ser.serialize(ans));
    }
  }
  