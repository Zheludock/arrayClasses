abstract class UserScript {
    public String name;
    public UserScript dependsOn;
    public boolean retryOnError = false;
    public int maxRetries = 0;

    public abstract boolean run();
}

class Script1 extends UserScript {
    public Script1() {
        this.name = "Script1";
        this.retryOnError = true;
        this.maxRetries = 3;
    }

    public boolean run() {
        // Логика выполнения Script1
        System.out.println("Running Script1...");
        return true; // Успешное выполнение
    }
}

class Script2 extends UserScript {
    public Script2() {
        this.name = "Script2";
    }

    public boolean run() {
        // Логика выполнения Script2
        System.out.println("Running Script2...");
        return true; // Успешное выполнение
    }
}

class Script3 extends UserScript {
    public Script3() {
        this.name = "Script3";
        this.retryOnError = true;
        this.maxRetries = 1;
    }

    public boolean run() {
        // Логика выполнения Script3
        System.out.println("Running Script3...");
        return false; // Завершение с ошибкой для демонстрации ретраи
    }
}