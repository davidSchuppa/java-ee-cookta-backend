package com.codecool.cookta.model;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    private String label;
    private String uri;
    private String image;

    public Recipe(String label, String uri, String image) {
        this.label = label;
        this.uri = uri;
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "label='" + label + '\'' +
                ", uri='" + uri + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
