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
                        "",
                        "2023-10-01"
                )
        );

        items.add(
                new ItemDetail(
                        "102",
                        "HTC G2",
                        "From HTC",
                        "",
                        "2023-10-02"
                )
        );

        items.add(
                new ItemDetail(
                        "103",
                        "Sony Xperia Explorer",
                        "From xperia",
                        "",
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
