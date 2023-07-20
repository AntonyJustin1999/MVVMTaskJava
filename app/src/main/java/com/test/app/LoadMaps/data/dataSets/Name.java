package com.test.app.LoadMaps.data.dataSets;

import com.google.gson.annotations.SerializedName;

public class Name {
    @SerializedName("common")
    String common = null;

    @SerializedName("official")
    String official = null;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }
}
