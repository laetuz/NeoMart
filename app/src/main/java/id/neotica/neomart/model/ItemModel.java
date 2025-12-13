package id.neotica.neomart.model;

/**
 * Created by ryomartin on 06/12/25.
 */

public class ItemModel {
    private String id;
    private String name;
    private String imageUrl;
    private String createdAt;

    public ItemModel(String id, String name, String imageUrl, String createdAt) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() { return name; }

}
