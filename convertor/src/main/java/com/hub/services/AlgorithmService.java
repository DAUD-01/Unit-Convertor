package com.hub.services;

import com.hub.core.AlgorithmEngine;

public class AlgorithmService {

    public final AlgorithmEngine engine = new AlgorithmEngine();

    public Object execute(String type, Object... inputs) {
        switch (type.toLowerCase()) {
            case "toroman":
                return engine.toRoman((int) inputs[0]);
            case "fromroman":
                return engine.fromRoman((String) inputs[0]);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + type);
        }
    }
}
