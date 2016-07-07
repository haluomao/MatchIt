package com.atongmu.matchit.algo;

import com.atongmu.matchit.entity.Point;
import com.atongmu.matchit.entity.Position;
import com.atongmu.matchit.entity.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mfg on 16/06/29.
 */
public class MatchIt {

    /**
     *       [2]
     *        l
     * [0]- - a-[1]
     *        l
     *       [3]
     *
     * @param points
     * @param a
     * @return
     */
    public static Position[] getAround(Point[][] points, Position a) {
        Position[] res = new Position[4];
        res[0] = res[1] = res[2] = res[3] = a;
        for (int y = a.getY() - 1; y >= 0; y--) {
            if (points[a.getX()][y].getValue() != 0)
                break;
            res[0] = points[a.getX()][y].getPosition();
        }
        for (int y = a.getY() + 1; y <= points[0].length - 1; y++) {
            if (points[a.getX()][y].getValue() != 0)
                break;
            res[1] = points[a.getX()][y].getPosition();
        }
        for (int x = a.getX() - 1; x >= 0; x--) {
            if (points[x][a.getY()].getValue() != 0)
                break;
            res[2] = points[x][a.getY()].getPosition();
        }
        for (int x = a.getX() + 1; x <= points.length - 1; x++) {
            if (points[x][a.getY()].getValue() != 0)
                break;
            res[3] = points[x][a.getY()].getPosition();
        }
        return res;
    }

    public static Solution match(Point[][] points, Position a, Position b) {
        Solution sol = new Solution();
        sol.setValue(Solution.WRONG);
        boolean swapped = false;
        if (b.getY() < a.getY() || (b.getY() == a.getY() && b.getX() < a.getX())) {
            Position c = a;
            a = b;
            b = c;
            swapped = true;
        }
        int aPosX = a.getX();
        int aPosY = a.getY();
        int bPosX = b.getX();
        int bPosY = b.getY();

        Position[] aAround = getAround(points, a);
        System.out.println("aAround:");
        for (Position position : aAround) {
            System.out.println(position);
        }

        Position[] bAround = getAround(points, b);
        System.out.println("bAround:");
        for (Position position : bAround) {
            System.out.println(position);
        }
        if (aPosY == bPosY) {
            /**
             *  - - a - -
             *  l # l #  l
             *  - - b - -
             */
            boolean flag = true;
            //直接能连
            if (aAround[3].getX() == b.getX() - 1) {
                sol.setValue(Solution.DOWN);
                if (swapped) sol.setValue(Solution.UP);
                return sol;
            } else if (aAround[1].getY() != a.getY() && bAround[1].getY() != b.getY()) {
                for (int y = a.getY() + 1; y <= aAround[1].getY() && y <= bAround[1].getY(); y++) {
                    flag = true;
                    int x = aPosX;
                    for (x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        sol.setValue(Solution.RIGHT_DOWN_LEFT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.RIGHT_UP_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            } else if (aAround[0].getY() != a.getY() && bAround[0].getY() != b.getY()) {
                for (int y = a.getY() - 1; y >= aAround[0].getY() && y >= bAround[0].getY(); y--) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        sol.setValue(Solution.LEFT_DOWN_RIGHT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.LEFT_UP_RIGHT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            } else {
                sol.setValue(Solution.WRONG);
            }
        } else if (aPosX == bPosX) {
            /**
             *  - - -
             * l     l
             * a - - b
             * l     l
             *  - - -
             */
            boolean flag = true;
            //直接能连
            if (aAround[1].getY() == b.getY() - 1) {
                sol.setValue(Solution.RIGHT);
                if (swapped) sol.setValue(Solution.LEFT);
                return sol;
            } else if (aAround[2].getX() != a.getX() && bAround[2].getX() != b.getX()) { //向上
                for (int x = a.getX() - 1; x >= aAround[2].getX() && x >= bAround[2].getX(); x--) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        sol.setValue(Solution.UP_RIGHT_DOWN);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.UP_LEFT_DOWN);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            } else if (aAround[3].getX() != a.getX() && bAround[3].getX() != b.getX()) {
                for (int x = a.getX() + 1; x <= aAround[3].getX() && x <= bAround[3].getX(); x++) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        sol.setValue(Solution.DOWN_RIGHT_UP);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.DOWN_LEFT_UP);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            } else {
                sol.setValue(Solution.WRONG);
            }
        } else if (aPosX < bPosX) {
            /**
             * a - -
             * l # l
             * - - b
             */
            boolean flag = true;
            //直接能连
            if (aAround[1].getY() >= b.getY() && bAround[2].getX() <= a.getX()) {
                //a 右 下 b
                sol.setValue(Solution.RIGHT_DOWN);
                sol.setPos1(new Position(aPosX, bPosY));
                if (swapped) {
                    sol.setValue(Solution.UP_LEFT);
                    sol.setPos1(new Position(aPosX, bPosY));
                }
                return sol;
            } else if (aAround[3].getX() >= b.getX() && bAround[0].getY() <= a.getY()) {
                //a 下 右 b
                sol.setValue(Solution.DOWN_RIGHT);
                sol.setPos1(new Position(bPosX, aPosY));
                if (swapped) {
                    sol.setValue(Solution.LEFT_UP);
                    sol.setPos1(new Position(bPosX, aPosY));
                }
                return sol;
            }
            if (aAround[1].getY() >= bAround[0].getY()) {
                //检查中间
                int lower = bAround[2].getX();
                if (lower < a.getX() + 1)
                    lower = a.getX() + 1;
                int upper = aAround[1].getY();
                if (upper > b.getY() + 1)
                    upper = b.getY() + 1;
                for (int y = lower; y <= upper; y++) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 右 下 右 b
                        sol.setValue(Solution.RIGHT_DOWN_RIGHT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.LEFT_UP_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            }
            if (bAround[0].getY() < a.getY()) {
                int y = bAround[0].getY();
                if (y < aAround[0].getY())
                    y = aAround[0].getY();
                for (; y < a.getY(); y++) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 左 下 右 b
                        sol.setValue(Solution.LEFT_DOWN_RIGHT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.RIGHT_UP_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            }
            if (aAround[1].getY() > b.getY()) {
                int y = aAround[1].getY();
                if (y > bAround[1].getY())
                    y = bAround[1].getY();
                for (; y > b.getY(); y--) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 右 下 左 b
                        sol.setValue(Solution.RIGHT_DOWN_LEFT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.LEFT_UP_RIGHT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            } else {
                //竖线没有
                sol.setValue(Solution.WRONG);
            }

            //检测横线
//            if (aAround[3].getX() <= b.getX() && bAround[2].getX() >= a.getX()) {
            if (aAround[3].getX() >= bAround[2].getX()) {
                //检查中间
                int lower = bAround[2].getX();
                if (lower < a.getX() + 1)
                    lower = a.getX() + 1;
                int upper = aAround[3].getX();
                if (upper > b.getX() + 1)
                    upper = b.getX() + 1;
                for (int x = lower; x <= upper; x++) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 下 右 下 b
                        sol.setValue(Solution.DOWN_RIGHT_DOWN);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.UP_LEFT_UP);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            }
            if (bAround[2].getX() < a.getX()) {
                int x = bAround[2].getX();
                if (x < aAround[2].getX())
                    x = aAround[2].getX();
                for (; x < a.getX(); x++) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 上 右 下 b
                        sol.setValue(Solution.UP_RIGHT_DOWN);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.UP_LEFT_DOWN);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            }
            if (aAround[3].getX() > b.getX()) {
                int x = aAround[3].getX();
                if (x > bAround[3].getX())
                    x = bAround[3].getX();
                for (; x > b.getX(); x--) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 下 右 上 b
                        sol.setValue(Solution.DOWN_RIGHT_UP);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.DOWN_LEFT_UP);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            } else {
                //横线没有
                sol.setValue(Solution.WRONG);
            }
        } else {// if (aPosX > bPosX)
            /**
             * - - - b
             * l # # l
             * a - - -
             */
            boolean flag = true;
            //直接能连
            if (aAround[1].getY() >= b.getY() && bAround[3].getX() >= a.getX()) {
                //a 右 上 b
                sol.setValue(Solution.RIGHT_UP);
                sol.setPos1(new Position(aPosX, bPosY));
                if (swapped) {
                    sol.setValue(Solution.DOWN_LEFT);
                    sol.setPos1(new Position(aPosX, bPosY));
                }
                return sol;
            } else if (aAround[2].getX() <= b.getX() && bAround[0].getY() <= a.getY()) {
                //a 上 右 b
                sol.setValue(Solution.UP_RIGHT);
                sol.setPos1(new Position(bPosX, aPosY));
                if (swapped) {
                    sol.setValue(Solution.LEFT_DOWN);
                    sol.setPos1(new Position(bPosX, aPosY));
                }
                return sol;
            }

            //检测竖线
            if (aAround[1].getY() >= bAround[0].getY()) {
                //检查中间
                int lower = bAround[0].getY();
                if (lower < a.getY() + 1)
                    lower = a.getY() + 1;
                int upper = aAround[1].getY();
                if (upper > b.getY() + 1)
                    upper = b.getY() + 1;
                for (int y = lower; y <= upper; y++) {
                    flag = true;
                    for (int x = bPosX; x <= aPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 右 上 右 b
                        sol.setValue(Solution.RIGHT_UP_RIGHT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.LEFT_DOWN_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            }

            if (bAround[0].getY() < a.getY()) {
                int y = bAround[0].getY();
                if (y < aAround[0].getY())
                    y = aAround[0].getY();
                for (; y < a.getY(); y++) {
                    flag = true;
                    for (int x = bPosX; x <= aPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 左 上 右 b
                        sol.setValue(Solution.LEFT_UP_RIGHT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.LEFT_DOWN_RIGHT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            }

            if (aAround[1].getY() > b.getY()) {
                int y = aAround[1].getY();
                if (y > bAround[1].getY())
                    y = bAround[1].getY();
                for (; y > b.getY(); y--) {
                    flag = true;
                    for (int x = bPosX; x <= aPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 右 上 左 b
                        sol.setValue(Solution.RIGHT_UP_LEFT);
                        sol.setPos1(new Position(aPosX, y));
                        sol.setPos2(new Position(bPosX, y));
                        if (swapped) {
                            sol.setValue(Solution.RIGHT_DOWN_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            } else {
                //竖线没有
                sol.setValue(Solution.WRONG);
            }

            //检测横线
            if (aAround[2].getX() <= bAround[3].getX()) {
                //检查中间
                int lower = aAround[2].getX();
                if (lower < b.getX() + 1)
                    lower = b.getX() + 1;
                int upper = bAround[3].getX();
                if (upper > a.getX() + 1)
                    upper = a.getX() + 1;
                for (int x = lower; x <= upper; x++) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 上 右 上 b
                        sol.setValue(Solution.UP_RIGHT_UP);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.DOWN_LEFT_DOWN);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            }
            if (bAround[3].getX() > a.getX()) {
                int x = bAround[3].getX();
                if (x > aAround[3].getX())
                    x = aAround[3].getX();
                for (; x > a.getX(); x--) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 下 右 上 b
                        sol.setValue(Solution.DOWN_RIGHT_UP);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.DOWN_LEFT_UP);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            }
            if (aAround[2].getX() < b.getX()) {
                int x = aAround[2].getX();
                if (x < bAround[2].getX())
                    x = bAround[2].getX();
                for (; x < b.getX(); x++) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 上 右 下 b
                        sol.setValue(Solution.UP_RIGHT_DOWN);
                        sol.setPos1(new Position(x, aPosY));
                        sol.setPos2(new Position(x, bPosY));
                        if (swapped) {
                            sol.setValue(Solution.UP_LEFT_DOWN);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            } else {
                sol.setValue(Solution.WRONG);
            }
        }
        return sol;
    }

    public static Solution checkCanSolve(Point[][] points){
        Solution solution = new Solution();
        Map<Integer, List<Point>> map = new HashMap<Integer, List<Point>>();

        int value=0;
        List<Point> list;
        for(int i=1; i<points.length-1; i++){
            for(int j=1; j<points[0].length-1; j++){
                value = points[i][j].getValue();
                if(value!=0) {
                    if (map.containsKey(value)){
                        map.get(value).add(points[i][j]);
                        if(map.get(value).size()>2) {
                            solution.setValue(Solution.CAN_SOLVE);
                            return solution;
                        }
                    }else{
                        list = new ArrayList<Point>();
                        list.add(points[i][j]);
                        map.put(value, list);
                    }
                }
            }
        }

        Position position1, position2;
        for(int key: map.keySet()){
            list  = map.get(key);

            for(int i=0; i<list.size(); i++){
                position1 = list.get(i).getPosition();
                for(int j=i+1; j<list.size(); j++) {
                    position2 = list.get(j).getPosition();
                    solution = match(points, position1, position2);
                    if(solution.getValue()!=Solution.WRONG){
                        solution.setPos1(position1);
                        solution.setPos2(position2);
                        return solution;
                    }
                }
            }
        }

        solution.setValue(Solution.WRONG);
        return solution;
    }

    public static Solution prompt(Point[][] points){
        Solution solution = new Solution();
        Map<Integer, List<Point>> map = new HashMap<Integer, List<Point>>();

        int value=0;
        List<Point> list;
        for(int i=1; i<points.length-1; i++){
            for(int j=1; j<points[0].length-1; j++){
                value = points[i][j].getValue();
                if(value!=0) {
                    if (map.containsKey(value)){
                        map.get(value).add(points[i][j]);
                    }else{
                        list = new ArrayList<Point>();
                        list.add(points[i][j]);
                        map.put(value, list);
                    }
                }
            }
        }

        Position position1, position2;
        for(int key: map.keySet()){
            list  = map.get(key);

            for(int i=0; i<list.size(); i++){
                position1 = list.get(i).getPosition();
                for(int j=i+1; j<list.size(); j++) {
                    position2 = list.get(j).getPosition();
                    solution = match(points, position1, position2);
                    if(solution.getValue()!=Solution.WRONG){
                        solution.setPos1(position1);
                        solution.setPos2(position2);
                        return solution;
                    }
                }
            }
        }

        solution.setValue(Solution.WRONG);
        return solution;
    }

    public static int[][] genMap(int x, int y, int[] nums) {
        if (x * y % 2 != 0) {
            System.out.println("invalid input!");
            return null;
        }
        int[][] res = new int[x + 2][y + 2];
        //random 生成随机位置和随机数字
        //直接用Collections里的洗牌函数得了
        List<Integer> cache = new ArrayList<Integer>();
        for (int i = 0; i < x * y / 2; i++) {
            int tmp = nums[i % nums.length];
            cache.add(tmp);
            cache.add(tmp);
        }
        System.out.println(cache);
        Collections.shuffle(cache);
        int index = 0;
        for (int i = 1; i < x + 1; i++) {
            for (int j = 1; j < y + 1; j++) {
                res[i][j] = cache.get(index);
                index++;
            }
        }

        for (int i = 0; i < x + 2; i++) {
            System.out.println();
            for (int j = 0; j < y + 2; j++) {
                System.out.print(res[i][j] + " ");
            }
        }
        return res;
    }

    public static void main(String[] args) {
        MatchIt matchIt = new MatchIt();
        int xSize = 10;
        int ySize = 10;
        int[] nums = {1, 2, 3, 9, 4};
        Point[][] points = new Point[xSize + 2][ySize + 2];
        int[][] numArr = matchIt.genMap(xSize, ySize, nums);
        for (int i = 0; i < xSize + 2; i++) {
            for (int j = 0; j < ySize + 2; j++) {
                int value = numArr[i][j];
                points[i][j] = new Point(i, j);
                points[i][j].setValue(value);
            }
        }

        System.out.println();
        for (int i = 0; i < xSize + 2; i++) {
            System.out.println();
            for (int j = 0; j < ySize + 2; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println();


        Solution solution = checkCanSolve(points);
        while(solution.getValue()!=Solution.WRONG){
            solution = prompt(points);

            System.out.println(solution.getPos1()+","+solution.getPos2());
            for (int i = 0; i < xSize + 2; i++) {
                System.out.println();
                for (int j = 0; j < ySize + 2; j++) {
                    System.out.print(points[i][j].getValue() + " ");
                }
            }
            System.out.println();
            points[solution.getPos1().getX()][solution.getPos1().getY()].setValue(0);
            points[solution.getPos2().getX()][solution.getPos2().getY()].setValue(0);

            solution = checkCanSolve(points);
        }

        System.out.println("无解了：");
        for (int i = 0; i < xSize + 2; i++) {
            System.out.println();
            for (int j = 0; j < ySize + 2; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println();
    }

    public static void main2(String[] args) {
        MatchIt matchIt = new MatchIt();
        int xSize = 4;
        int ySize = 4;
        int[] nums = {1, 2, 3, 9, 4};
        Point[][] points = new Point[xSize + 2][ySize + 2];
        int[][] numArr = matchIt.genMap(xSize, ySize, nums);
        for (int i = 0; i < xSize + 2; i++) {
            for (int j = 0; j < ySize + 2; j++) {
                int value = numArr[i][j];
                points[i][j] = new Point(i, j);
                points[i][j].setValue(value);
            }
        }
        System.out.println();
        for (int i = 0; i < xSize + 2; i++) {
            System.out.println();
            for (int j = 0; j < ySize + 2; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println();

        System.out.println();
        for (int i = 0; i < xSize + 2; i++) {
            System.out.println();
            for (int j = 0; j < ySize + 2; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println();



//        for(int i=1; i < xSize+2; i++) {
//            for (int j = 1; j < ySize; j++) {
//                Position a = new Position(i,j);
//                Position b = new Position(i,j+1);
//                Solution tmp = matchIt.match(points, a, b);
//                System.out.println(a+" "+b+" "+tmp);
//            }
//        }

        Position a = new Position(1, 1);
        Position b = new Position(3, 3);
        points[2][1].setValue(0);
        points[2][2].setValue(0);
        points[2][3].setValue(0);
        points[3][1].setValue(0);

        System.out.println();
        for (int i = 0; i < xSize + 2; i++) {
            System.out.println();
            for (int j = 0; j < ySize + 2; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println();

        Solution tmp = matchIt.match(points, a, b);
        System.out.println(a + " " + b + " " + tmp);
    }
}
