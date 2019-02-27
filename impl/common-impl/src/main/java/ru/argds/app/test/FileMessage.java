package ru.argds.app.test;

import java.util.Objects;

public class FileMessage implements Comparable<FileMessage>{

    private int priority;
    private long order;
    private String pathFileName;

    public static FileMessage createInstance(int priority, long order, String pathFileName){
        return new FileMessage(priority, order, pathFileName);
    }

    private FileMessage(int priority, long order, String pathFileName) {
        this.priority = priority;
        this.order = order;
        this.pathFileName = pathFileName;
    }

    @Override
    public int compareTo(FileMessage o) {

        return this.priority == o.priority ? Long.compare(this.order, o.order) : Integer.compare(o.priority, this.priority);
    }

    public int getPriority() {
        return priority;
    }

    public long getOrder() {
        return order;
    }

    public String getPathFileName() {
        return pathFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMessage that = (FileMessage) o;
        return priority == that.priority &&
                order == that.order &&
                Objects.equals(pathFileName, that.pathFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority, order, pathFileName);
    }

    @Override
    public String toString() {
        return "FileMessage{" +
                "priority=" + priority +
                ", order=" + order +
                ", pathFileName='" + pathFileName + '\'' +
                '}';
    }
}