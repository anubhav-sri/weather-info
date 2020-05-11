package com.tenera.weather.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LimitedSelfHealingListFor5ElementsTest {

    @Test
    void shouldRemoveTheFirstAddedElementWhenSizeIncreaseMoreThan5() {
        LimitedSelfHealingListFor5Elements<Integer> queue = new LimitedSelfHealingListFor5Elements<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);

        assertThat(queue.size()).isEqualTo(5);
        assertThat(queue).containsExactly(2, 3, 4, 5, 6);

    }

}