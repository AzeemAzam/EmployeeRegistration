package com.test.testsat.Models;

public class putPDF {

    public String namee;
    public String uri;

    public putPDF() {
    }

    public putPDF(String namee, String uri) {
        this.namee = namee;
        this.uri = uri;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
