package se.peterboberg.montyhall.game;


import se.peterboberg.montyhall.model.Box;
import se.peterboberg.montyhall.model.BoxNumber;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public interface MontyHallGame {

    List<BoxNumber> ALL_BOX_NUMBERS = Collections.unmodifiableList(Arrays.asList(BoxNumber.ONE, BoxNumber.TWO, BoxNumber.THREE));

    /**
     * This method lets the game know what <tt>BoxNumber</tt> to select out of three possible ones.
     * The implementation then discards one of the remaining two boxes that is guaranteed to be empty and
     * offers the selected box together with the single remaining "alternate" box to the <tt>BoxReceiver</tt>.
     * One of them is guaranteed to contain money.
     *
     * Its up to the implementer to make sure that a box containing "Money inside" is given as the "alternate" box
     * if the player selects a box without money.
     *
     * If the player selects the box containing money in the first place, any of the two remaining boxes can be offered as "alternate".
     *
     * @param boxNumber   the <tt>BoxNumber</tt> to select One, TWO, or THREE.
     * @param boxReceiver the receiver of the selected <tt>Box</tt> along with a randomly chosen <tt>Box</tt> from the remaining ones
     */
    void selectBoxAndReceiveOffer(BoxNumber boxNumber, BoxReceiver boxReceiver);

    /**
     * Selects a random <tt>BoxNumber</tt> out of three exising ones: ONE, TWO, THREE
     *
     * @return the a random <tt>BoxNumber</tt>
     */

    static BoxNumber randomBoxNumber() {
        return ALL_BOX_NUMBERS.get(new Random().nextInt(ALL_BOX_NUMBERS.size()));
    }


    interface BoxReceiver {

        /**
         * Receives the selected <tt>Box</tt> along with a randomly chosen one from the remaining boxes. The implementer of
         * <tt>selectBoxAndReceiveOffer(BoxNumber boxNumber, BoxReceiver boxReceiver)</tt> needs to make sure that a box having
         * <tt>isMoneyInside()</tt> set to true among one and only one of the offered boxes. It is up to the <tt>BoxReceiver</tt>
         * to choose whether to stick to the first selected <tt>Box</tt> or to choose the alternate one.
         *
         * @param selectedBox  the selected box
         * @param alternateBox the alternate box
         */
        void offer(Box selectedBox, Box alternateBox);
    }
}
