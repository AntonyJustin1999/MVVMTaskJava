package com.test.app.LoadMaps.data.dataSets;

import com.google.gson.annotations.SerializedName;

public class CoatOfArms {
    @SerializedName("png")
    String png_url = null;

    @SerializedName("svg")
    String svg_url = null;

    public String getPng_url() {
        return png_url;
    }

    public void setPng_url(String png_url) {
        this.png_url = png_url;
    }

    public String getSvg_url() {
        return svg_url;
    }

    public void setSvg_url(String svg_url) {
        this.svg_url = svg_url;
    }
}
