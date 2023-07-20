package com.test.app.LoadMaps.data.dataSets;

import com.google.gson.annotations.SerializedName;

public class CountriesApi {
    @SerializedName("flags")
    Flags flags = null;

    @SerializedName("name")
    Name name = null;

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}




