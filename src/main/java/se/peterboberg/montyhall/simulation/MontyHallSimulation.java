package se.peterboberg.montyhall.simulation;

import se.peterboberg.montyhall.model.SimulationResult;

import java.math.BigInteger;

public interface MontyHallSimulation {


    /**
     * Starts the simulation of the monty hall game and keeps simulating the scenario for as many times as defined by
     * <tt>numberOfSimulations</tt>. The simulations should preferably be run on a background thread and use the
     * methods defined in the <tt>Delegate</tt> to update any delegate about the progress percent and result of
     * the simulations.
     *
     * @param numberOfSimulations the number of simulations to run
     * @throws IllegalStateException if no delegate is set.
     */
    void start(BigInteger numberOfSimulations);

    /**
     * Stops any occurring simulations if present.
     */
    void stop();

    /**
     * Sets the delegate to notify about simulation progress percent and simulation result.
     *
     * @param delegate the delegate.
     */
    void setDelegate(Delegate delegate);

    interface Delegate {

        /**
         * Continuously updates the <tt>Delegate</tt> about the progress percent of all the simulations. This method gets called from a
         * background thread and its up to the implementer to make sure that any actions inside it are pushed to an
         * appropriate thread (For example if the <tt>Delegate</tt> is GUI)
         *
         * @param percentDone the percent done out of all simulations.
         */
        void onSimulationUpdateProgress(double percentDone);

        /**
         * Notifies the <tt>Delegate</tt> about the result of all the simulations. The data is encapsulated in a <tt>SimulationResult</tt>
         * object. This method gets called from a background thread and its up to the implementer to make sure that any actions inside
         * it are pushed to an appropriate thread (For example if the <tt>Delegate</tt> is GUI)
         *
         * @param result an object containing data about the result of the simulations.
         */
        void onSimulationDone(SimulationResult result);
    }
}
