package se.peterboberg.montyhall.game;

import se.peterboberg.montyhall.model.Box;
import se.peterboberg.montyhall.model.BoxNumber;

import java.util.*;
import java.util.stream.Collectors;

public class MontyHallGameImpl implements MontyHallGame {

    @Override
    public void selectBoxAndReceiveOffer(BoxNumber boxNumber, BoxReceiver boxReceiver) {
        Map<BoxNumber, Box> boxes = loadBoxes();
        Box selectedBox = boxes.remove(boxNumber);
        List<Box> remainingBoxes = new ArrayList<>(boxes.values());
        Box alternateBox;
        if (selectedBox.isMoneyInside()) {
            // Pick any of the two remaining boxes as alternate box
            alternateBox = remainingBoxes.get(new Random().nextInt(remainingBoxes.size()));

        } else {
            // Find the box containing money and assign that to alternate box
            alternateBox = remainingBoxes.stream()
                    .filter(box -> box.isMoneyInside())
                    .collect(Collectors.toList()).get(0);
        }

        boxReceiver.offer(selectedBox, alternateBox);
    }

    private Map<BoxNumber, Box> loadBoxes() {
        Map<BoxNumber, Box> boxes = new HashMap<>();
        boxes.put(BoxNumber.ONE, new Box(BoxNumber.ONE));
        boxes.put(BoxNumber.TWO, new Box(BoxNumber.TWO));
        boxes.put(BoxNumber.THREE, new Box(BoxNumber.THREE));
        boxes.get(MontyHallGame.randomBoxNumber()).setMoneyInside(true);
        return boxes;
    }
}
