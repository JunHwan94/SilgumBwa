package com.junhwan;

public class SearchWordItem {
    public String rank;
    public String searchWord;
    public int colorCode;

    public SearchWordItem(String rank, String searchWord, int colorCode) {
        this.rank = rank;
        this.searchWord = searchWord;
        this.colorCode = colorCode;
    }

    public String getRank() {
        return rank;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public int getColorCode() {
        return colorCode;
    }
}