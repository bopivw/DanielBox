package vdatta.us.danielbox.util.task;

import lombok.Setter;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ScheduleRepeatingTask {
    private final int secondsStartDelay;
    private final float secondsBetween;
    private final int repetitions;

    private int index;

    private final Consumer<Integer> runnable;
    private final Timer timer;

    @Setter private Runnable endRunnable;

    public ScheduleRepeatingTask(final Consumer<Integer> runnable, final int secondsStartDelay, final float secondsBetween, final int repetitions) {
        this.secondsStartDelay = secondsStartDelay * 1000;
        this.secondsBetween = secondsBetween * 1000;

        this.repetitions = repetitions;

        this.runnable = runnable;
        this.timer = new Timer();

        this.index = repetitions;
    }
    public ScheduleRepeatingTask start() {
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (index < 1) {
                    if (!Objects.isNull(endRunnable)) {
                        System.out.println(1);
                        endRunnable.run();
                    }

                    timer.cancel();
                }

                runnable.accept(index);

                index--;
            }
        }, this.secondsStartDelay, (long) this.secondsBetween);
        return this;
    }
}