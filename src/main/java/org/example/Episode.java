package org.example;

import java.time.Duration;

public class Episode {

    public Episode(String name, String runtime) {
        this.name = name;
        this.runtime = runtime;
    }

    private String name;
    private String runtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "name='" + name + '\'' +
                ", runtime='" + runtime + '\'' +
                '}';
    }
}
