package com.guesstheurf.guesstheurf.models;

public class HighscoreRequestParameters {
    private int page;
    private int pageSize;

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public HighscoreRequestParameters(int page, int pageSize) {

        this.page = page;
        this.pageSize = pageSize;
    }
}
