public class Duke {
    public static void main(String[] args) {
        String logo =   "_|_|_|_|_|    _|_|                _|_|_|      _|_|     _|  \n" +
                        "    _|      _|    _|              _|    _|  _|    _|   _|  \n" +
                        "    _|      _|    _|  _|_|_|_|_|  _|    _|  _|    _|   _|  \n" +
                        "    _|      _|    _|              _|    _|  _|    _|       \n" +
                        "    _|        _|_|                _|_|_|      _|_|     _|  ";
        System.out.println("Hello from\n" + logo);

        System.out.println("What you are going to do today?");

        ToDoList.start();
    }
}
