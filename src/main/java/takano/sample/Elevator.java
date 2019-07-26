package takano.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Elevator {

    public static void main(String[] args) throws Exception {
        final int moveCnt;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        moveCnt = Integer.parseInt(br.readLine());

        int totalMoveDistance = 0;

        int currentPos = 1;
        for (int i = 0; i < moveCnt; i++) {
            final int destPos = Integer.parseInt(br.readLine());
            totalMoveDistance += Math.abs(currentPos - destPos);
            currentPos = destPos;
        }

        System.out.println(totalMoveDistance);
    }

}
