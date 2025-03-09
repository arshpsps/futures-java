package org.arshpsps;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Future f = null;
    }
}

interface Future {
    public Object poll(Object data);

    default public Then then(Function<Object, Future> function) {
        return new Then() {
            {
                in = this;
                func = function;
            }
        };
    }
}

class Then implements Future {
    public Future in;
    public Future out;
    public Function<Object, Future> func;

    public Object poll(Object data) {
        if (this.in != null) {
            Object result = this.in.poll(data);
            if (result != null) {
                this.out = this.func.apply(result);
                this.in = null;
            }
            return null;
        } else {
            if (this.out == null) {
                System.out.println("this.out is null");
                return false;
            }
            return this.out.poll(data);
        }
    }
}
