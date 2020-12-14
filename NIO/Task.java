import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Task {
    private int clientId;
    private int connectionNumber;
    private String operator;
    private int first;
    private int second;
    private int result;
    SocketChannel client_Socket;

    Task(){}

    Task(int clientId, int connectionNumber, String operator, int first, int second, SocketChannel client){
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

    public void SetOperator(String Operator) { this.operator = operator; }

    public void SetFirst(int first)
    {
        this.first = first;
    }

    public void SetSecond(int second)
    {
        this.second = second;
    }

    public void SetResult(int result)
    {
        this.result = result;
    }

    public void SetSocket(SocketChannel socket)
    {
        this.client_Socket = socket;
    }

    public void SetClientId(int ClientID)
    {
        this.clientId = clientId;
    }

    public int GetResult() {return this.result; }

    public SocketChannel GetSocket() {return this.client_Socket; }
}
