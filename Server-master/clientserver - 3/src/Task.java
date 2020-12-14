import java.net.Socket;

public class Task {
    private int clientId;
    private int connectionNumber;
    private String operator;
    private int first;
    private int second;
    private int result;
    Socket client_Socket;

    Task(){}

    Task(int clientId, int connectionNumber, String operator, int first, int second, Socket client){
        this.clientId = clientId;
        this.connectionNumber = connectionNumber;
        this.operator = operator;
        this.first = first;
        this.second = second;
        this.client_Socket = client;
        this.result = 0;
    }

    public String GetOperator()
    {
        return this.operator;
    }

    public int GetFirst()
    {
        return this.first;
    }

    public int GetSecond()
    {
        return this.second;
    }

    public void SetResult(int result)
    {
        this.result = result;
    }

    public int GetResult() {return this.result; }

    public Socket GetSocket() {return this.client_Socket; }
}
