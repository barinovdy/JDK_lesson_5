package org.geekbrains.Homework.v1;

//Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом стоит тарелка спагетти.
//Вилки лежат на столе между каждой парой ближайших философов.
//Каждый философ может либо есть, либо размышлять.
//Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
//Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> philosopherNames = new ArrayList<>(Arrays.asList(
                "Сократ", "Платон", "Аристотель", "Пифагор", "Гиппократ"
        ));

        new Thread(new Table(philosopherNames)).start();

    }
}
