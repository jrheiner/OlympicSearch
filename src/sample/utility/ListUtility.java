package sample.utility;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ListUtility {

    public static String arrayToStringDisplay(ArrayList<?> arrayList) {
        String arrayString = arrayList.toString();
        return arrayString.substring(1, arrayString.length() - 1).replaceAll("-1.0|-1", "Not available");
    }

    public static TreeSet<String> searchInSet(String term, Set<String> baseSet) {
        TreeSet<String> resultSet = new TreeSet<>();
        baseSet.forEach(entry -> {
            if (entry.contains(term)) {
                resultSet.add(entry);
            }
        });
        return resultSet;
    }
}
