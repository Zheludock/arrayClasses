import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScriptExecutor {

    private static List<UserScript> sortedScripts = new ArrayList<>();

    public static void executeScripts(UserScript[] scripts) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (UserScript script : sortedScripts) {
            Runnable task = () -> {
                boolean success = false;
                int retries = 0;
                do {
                    success = script.run();
                    if (!success && script.retryOnError && retries < script.maxRetries) {
                        System.out.println("Script '" + script.name + "' failed, retrying...");
                        retries++;
                    } else {
                        break;
                    }
                } while (true);
            };
            executor.execute(task);
        }

        executor.shutdown();
    }

    public static void main(String[] args) {
        UserScript[] scripts = new UserScript[]{
                new Script1(),
                new Script2(),
                new Script3()
                // Добавьте скрипты сюда
        };

        try {
            sortScripts(scripts);
            executeScripts(scripts);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void sortScripts(UserScript[] scripts) {
        sortedScripts.clear();
        for (UserScript script : scripts) {
            visit(script, new ArrayList<>());
        }
    }

    private static void visit(UserScript script, List<UserScript> visited) {
        if (visited.contains(script)) {
            throw new RuntimeException("Circular dependency detected involving script: " + script.getClass().getSimpleName());
        }

        if (!sortedScripts.contains(script)) {
            visited.add(script);
            if (script.dependsOn != null) {
                visit(script.dependsOn, visited);
            }
            sortedScripts.add(script);
            visited.remove(script);
        }
    }
}
