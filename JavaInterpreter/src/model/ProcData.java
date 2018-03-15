package model;

import model.statement.Statement;

import java.util.List;

public class ProcData {
    private List<String> parameters;
    private Statement body;

    public ProcData(List<String> parameters, Statement body) {
        this.parameters = parameters;
        this.body = body;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Statement getBody() {
        return body;
    }

    public void setBody(Statement body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ProcData{" +
                "parameters=" + parameters +
                ", body=" + body +
                '}';
    }
}
