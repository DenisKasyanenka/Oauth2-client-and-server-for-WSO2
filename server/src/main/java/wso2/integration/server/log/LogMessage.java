package wso2.integration.server.log;

public class LogMessage {

    private int httpStatus;
    private String httpMethod;
    private String path;
    private String clientIp;
    private String javaMethod;
    private String response;

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "LogMessage{ \n" +
                "httpStatus=" + httpStatus + "\n" +
                ", httpMethod='" + httpMethod + '\'' + "\n" +
                ", path='" + path + '\'' + "\n" +
                ", clientIp='" + clientIp + '\'' + "\n" +
                ", javaMethod='" + javaMethod + '\'' + "\n" +
                ", response='" + response + '\'' + "\n" +
                '}' + "\n";
    }
}
