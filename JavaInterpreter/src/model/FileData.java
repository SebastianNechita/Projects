package model;

import java.io.BufferedReader;

public class FileData {
    private String fileName;
    private BufferedReader fileDescriptor;

    @Override
    public String toString() {
        return "FileData{" +
                "fileName='" + fileName + '\'' +
                ", fileDescriptor=" + fileDescriptor +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BufferedReader getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(BufferedReader fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

    public FileData(String fileName, BufferedReader fileDescriptor) {

        this.fileName = fileName;
        this.fileDescriptor = fileDescriptor;
    }
}
