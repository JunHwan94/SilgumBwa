package com.junhwan;

public class SearchWordItem {
    public String rank;
    public String searchWord;

    public SearchWordItem(String rank, String searchWord) {
        this.rank = rank;
        this.searchWord = searchWord;
    }

    public String getRank() {
        return rank;
    }

    public String getSearchWord() {
        return searchWord;
    }

}
