package id.neotica.neomart.data;

import java.util.ArrayList;
import java.util.List;

import id.neotica.neomart.model.ItemDetail;

/**
 * Created by ryomartin on 06/12/25.
 */

public class DummyRepository {
    public static List<ItemDetail> getItemList() {
        List<ItemDetail> items = new ArrayList<>();

        items.add(
                new ItemDetail(
                        "101",
                        "Samsung Galaxy S2",
                        "From samsung",
//                        "https://fdn2.gsmarena.com/vv/pics/samsung/samsung-galaxy-s-ii-real-ofic-1.jpg",
                        "http://192.168.0.106:8080/projects/stacks/java-icon.svg",
                        200L,
                        "2023-10-01"
                )
        );

        items.add(
                new ItemDetail(
                        "102",
                        "HTC Magic",
                        "From HTC",
//                        "https://fdn2.gsmarena.com/vv/pics/htc/htc-magic-00.jpg",
                        "http://192.168.0.106:8080/projects/neoverse-logo.png",
                        250L,
                        "2023-10-02"
                )
        );

        items.add(
                new ItemDetail(
                        "103",
                        "Sony Xperia P",
                        "From sony",
                        "https://fdn2.gsmarena.com/vv/pics/sony/sony-xperia-p-silver.jpg",
                        300L,
                        "2023-10-03"
                )
        );

        return items;
    }

    public static ItemDetail getItemDetail(String targetId) {
        List<ItemDetail> allItems = getItemList();

        for (ItemDetail item: allItems) {
            if (item.getId().equals(targetId)) { return item; }
        }

        return null;
    }
}
