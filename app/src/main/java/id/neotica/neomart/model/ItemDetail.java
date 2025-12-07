package id.neotica.neomart.model;

/**
 * Created by ryomartin on 06/12/25.
 */

public class ItemDetail {
    private String id;
    private String name;
    private String desc;
    private String imageUrl;
    private String createdAt;

    public ItemDetail(
            String id, String name, String desc, String imageUrl,
            String createdAt
    ) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public String getImageUrl() { return imageUrl; }
    public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() { return name; }
}
