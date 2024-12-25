package com.microservice.serviceA.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceTime {
    MORNING("9am - 12pm", 12),
    AFTERNOON("12am - 3pm", 15),
    EVENING("3pm - 6pm", 18),
    NIGHT("6pm - 9pm", 21);

    public final String timeEstimation;
    public final int maxTime;
}
