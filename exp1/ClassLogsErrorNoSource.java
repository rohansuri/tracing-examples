package exp1;
/*
When we're getting logs from a class whose source we don't have.
We want to find what code path is taken to log this line.
Taking thread dumps isn't feasible since we'd have to time our jstack issuance right when we're
in the codepath for the error logging line.
Log line alone too is insufficient because it only has the class name that is logging, not the code path.
(not the method)

sudo /usr/share/bcc/tools/trace 'SyS_write (arg1==1) "%s", arg2' -U -p `pgrep -f ClassLogsErrorNoSource`
sudo -E perf-java-flames `pgrep -f ClassLogsErrorNoSource`
grep foo /tmp/perf-`pgrep -f ClassLogsErrorNoSource`.map

Following Sasha's first example in his JPoint talk.
 */

/*
checkout options that could be passed to perf-map-agent by browsing source of:
perf-map-agent.c

try using the newer jvmti agent support perf itself has out of the box?
https://github.com/torvalds/linux/tree/master/tools/perf/jvmti
 */
public class ClassLogsErrorNoSource {
    public static void main(String[] args) throws Exception {
        System.out.println("Start tracing and then press return here.");
        System.in.read();
        System.out.println("Program started");

        while(true){
            foo();
            Thread.sleep(4_000);
        }

    }

    public static void foo(){
        bar();
    }

    public static void bar(){
        baz();
    }

    public static void baz(){
        System.out.println("Can't do X...ERROR");
    }
}
