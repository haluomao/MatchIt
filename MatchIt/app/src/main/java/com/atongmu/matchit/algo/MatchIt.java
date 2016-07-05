package com.atongmu.matchit.algo;

import com.atongmu.matchit.entity.Point;
import com.atongmu.matchit.entity.Position;
import com.atongmu.matchit.entity.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mfg on 16/06/29.
 */
public class MatchIt {

    //待测试算法
    /**
     *       [2]
     *        l
     * [0]- - a-[1]
     *        l
     *       [3]
     * @param points
     * @param a
     * @return
     */
    public static Position[] getAround(Point[][] points, Position a){
        Position[] res = new Position[4];
        res[0] = res[1] = res[2] = res[3] = a;
        for(int y=a.getY()-1; y>=0;y--){
            if(points[a.getX()][y].getValue()!=0)
                break;
            res[0] = points[a.getX()][y].getPosition();
        }
        for(int y=a.getY()+1; y<=points[0].length-1;y++){
            if(points[a.getX()][y].getValue()!=0)
                break;
            res[1] = points[a.getX()][y].getPosition();
        }
        for(int x=a.getX()-1; x>=0;x--){
            if(points[x][a.getY()].getValue()!=0)
                break;
            res[2] = points[x][a.getY()].getPosition();
        }
        for(int x=a.getX()+1; x<=points.length-1;x++){
            if(points[x][a.getY()].getValue()!=0)
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
        for(Position position:aAround){
            System.out.println(position);
        }

        Position[] bAround = getAround(points, b);
        System.out.println("bAround:");
        for(Position position:bAround){
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
            if (aAround[3].getX() == b.getX()-1) {
                sol.setValue(Solution.DOWN);
                if(swapped) sol.setValue(Solution.UP);
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
                        if(swapped){
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
                        if(swapped){
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
            if (aAround[1].getY() == b.getY()-1) {
                sol.setValue(Solution.RIGHT);
                if(swapped) sol.setValue(Solution.LEFT);
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
                        if(swapped){
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
                        if(swapped){
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
                if(swapped){
                    sol.setValue(Solution.UP_LEFT);
                    sol.setPos1(new Position(aPosX, bPosY));
                }
                return sol;
            } else if (aAround[3].getX() >= b.getX() && bAround[0].getY() <= a.getY()) {
                //a 下 右 b
                sol.setValue(Solution.DOWN_RIGHT);
                sol.setPos1(new Position(bPosX, aPosY));
                if(swapped){
                    sol.setValue(Solution.LEFT_UP);
                    sol.setPos1(new Position(bPosX, aPosY));
                }
                return sol;
            } else if (aAround[1].getY() >= bAround[0].getY()){
//            } else if (aAround[1].getY() <= b.getY() && bAround[0].getY() >= a.getY()) {
//                if (aAround[1].getY() < bAround[0].getY()) {
//                    //中间连不上
//                } else {
                    for (int y = bAround[0].getY(); y <= aAround[1].getY(); y++) {
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
                            if(swapped){
                                sol.setValue(Solution.LEFT_UP_LEFT);
                                sol.setPos2(new Position(aPosX, y));
                                sol.setPos1(new Position(bPosX, y));
                            }
                            return sol;
                        }
                    }
//                }
            } else if (bAround[0].getY() < a.getY()) {
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
                        if(swapped){
                            sol.setValue(Solution.RIGHT_UP_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            } else if (aAround[1].getY() > b.getY()) {
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
                        if(swapped){
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
//                if (aAround[3].getX() < bAround[2].getX()) {
//                    //中间连不上
//                } else {
                    for (int x = bAround[2].getX(); x <= aAround[3].getX(); x++) {
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
                            if(swapped){
                                sol.setValue(Solution.UP_LEFT_UP);
                                sol.setPos2(new Position(x, aPosY));
                                sol.setPos1(new Position(x, bPosY));
                            }
                            return sol;
                        }
//                    }
                }
            } else if (bAround[2].getX() < a.getX()) {
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
                        if(swapped){
                            sol.setValue(Solution.UP_LEFT_DOWN);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            } else if (aAround[3].getX() > b.getX()) {
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
                        if(swapped){
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
        } else{// if (aPosX > bPosX)
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
                if(swapped){
                    sol.setValue(Solution.DOWN_LEFT);
                    sol.setPos1(new Position(aPosX, bPosY));
                }
                return sol;
            } else if (aAround[2].getX() <= b.getX() && bAround[0].getY() <= a.getY()) {
                //a 上 右 b
                sol.setValue(Solution.UP_RIGHT);
                sol.setPos1(new Position(bPosX, aPosY));
                if(swapped){
                    sol.setValue(Solution.LEFT_DOWN);
                    sol.setPos1(new Position(bPosX, aPosY));
                }
                return sol;
            }

            //检测竖线
            if (aAround[1].getY() >= bAround[0].getY()) {
//                if (aAround[1].getY() <= b.getY() && bAround[0].getY() >= a.getY()) {
//                if (aAround[1].getY() < bAround[0].getY()) {
//                    //中间连不上
//                } else {
                    for (int y = bAround[0].getY(); y <= aAround[1].getY(); y++) {
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
                            if(swapped){
                                sol.setValue(Solution.LEFT_DOWN_LEFT);
                                sol.setPos2(new Position(aPosX, y));
                                sol.setPos1(new Position(bPosX, y));
                            }
                            return sol;
                        }
                    }
//                }
            } else if (bAround[0].getY() < a.getY()) {
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
                        if(swapped){
                            sol.setValue(Solution.LEFT_DOWN_RIGHT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                        return sol;
                    }
                }
            } else if (aAround[1].getY() > b.getY()) {
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
                        if(swapped){
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
//            if (aAround[2].getX() >= b.getX() && bAround[3].getX() <= a.getX()) {
//                if (aAround[2].getX() > bAround[3].getX()) {
//                    //中间连不上
//                } else {
                    for (int x =aAround[2].getX(); x <= bAround[3].getX(); x++) {
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
                            if(swapped){
                                sol.setValue(Solution.DOWN_LEFT_DOWN);
                                sol.setPos2(new Position(x, aPosY));
                                sol.setPos1(new Position(x, bPosY));
                            }
                            return sol;
                        }
//                    }
                }
            } else if (bAround[3].getX() > a.getX()) {
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
                        if(swapped){
                            sol.setValue(Solution.DOWN_LEFT_UP);
                            sol.setPos2(new Position(x, aPosY));
                            sol.setPos1(new Position(x, bPosY));
                        }
                        return sol;
                    }
                }
            } else if (aAround[2].getX() < b.getX()) {
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
                        if(swapped){
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

    public boolean match2(Point[][] points, Position a, Position b) {
        Solution sol = new Solution();
        boolean res = false;
        boolean swaped = false;
        if (b.getY() < a.getY() || (b.getY() == a.getY() && b.getX() < a.getX())) {
            Position c = a;
            a = b;
            b = c;
            swaped = true;
        }
        int aPosX = a.getX();
        int aPosY = a.getY();
        int bPosX = b.getX();
        int bPosY = b.getY();

        Position[] aAround = getAround(points, a);
//        System.out.println("aAround:");
//        for(Position position:aAround){
//            System.out.println(position);
//        }

        Position[] bAround = getAround(points, b);
//        System.out.println("bAround:");
//        for(Position position:bAround){
//            System.out.println(position);
//        }
        if (aPosY == bPosY) {
            /**
             *  - - a - -
             *  l # l #  l
             *  - - b - -
             */
            boolean flag = true;
            //直接能连
            if (aAround[3].getX() == b.getX()-1) {
                sol.setValue(Solution.LEFT);
                if(swaped) sol.setValue(Solution.RIGHT);
            } else if (aAround[1].getY() != a.getY() && bAround[1].getY() != b.getY()) {
                for (int y = a.getY() + 1; y < aAround[1].getY() && y < bAround[1].getY(); y++) {
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
                        if(swaped){
                            sol.setValue(Solution.RIGHT_UP_LEFT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
                    }
                }
            } else if (aAround[0].getY() != a.getY() && bAround[0].getY() != b.getY()) {
                for (int y = a.getY() - 1; y > aAround[0].getX() && y > bAround[0].getY(); y--) {
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
                        if(swaped){
                            sol.setValue(Solution.LEFT_UP_RIGHT);
                            sol.setPos2(new Position(aPosX, y));
                            sol.setPos1(new Position(bPosX, y));
                        }
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
            if (aAround[1].getY() == b.getY()-1) {
                sol.setValue(Solution.LEFT);
                if(swaped) sol.setValue(Solution.RIGHT);
            } else if (aAround[2].getY() != a.getY() && bAround[2].getY() != b.getY()) { //向上
                for (int y = a.getY() - 1; y > aAround[2].getY() && y > bAround[2].getY(); y--) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        return flag;
                    }
                }
                return flag;
            } else if (aAround[3].getY() != a.getY() && bAround[3].getY() != b.getY()) {
                for (int y = a.getY() + 1; y < aAround[3].getY() && y < bAround[3].getY(); y++) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        return flag;
                    }
                }
                return flag;
            } else {
                return flag;
            }
        } else if (aPosY < bPosY) {
            /**
             * a - -
             * l # l
             * - - b
             */
            boolean flag = true;
            //直接能连
            if (aAround[1].getX() >= b.getX() && bAround[2].getY() >= a.getY()) {
                //a 右 下 b
                return flag;
            } else if (aAround[3].getX() <= b.getX() && bAround[0].getY() <= a.getY()) {
                //a 下 右 b
                return flag;
            } else if (aAround[1].getX() <= b.getX() && bAround[0].getX() >= a.getX()) {
                if (aAround[1].getX() < bAround[0].getX()) {
                    //中间连不上
                } else {
                    for (int x = bAround[0].getX(); x <= aAround[1].getX(); x++) {
                        flag = true;
                        for (int y = aPosY; y <= bPosY; y++) {
                            if (points[x][y].getValue() != 0) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            //a 右 下 右 b
                            return flag;
                        }
                    }
                }
            } else if (bAround[0].getX() < a.getX()) {
                int x = bAround[0].getX();
                if (x < aAround[0].getX())
                    x = aAround[0].getX();
                for (; x < a.getX(); x++) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 左 下 右 b
                        return flag;
                    }
                }
            } else if (aAround[1].getX() > b.getX()) {
                int x = aAround[1].getX();
                if (x > bAround[1].getX())
                    x = bAround[1].getX();
                for (; x > b.getX(); x--) {
                    flag = true;
                    for (int y = aPosY; y <= bPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 右 下 左 b
                        return flag;
                    }
                }
            } else {
                //竖线没有
            }

            //检测横线
            if (aAround[3].getY() <= b.getY() && bAround[2].getY() >= a.getY()) {
                if (aAround[3].getY() < bAround[2].getY()) {
                    //中间连不上
                } else {
                    for (int y = bAround[2].getY(); y <= aAround[3].getY(); y++) {
                        flag = true;
                        for (int x = aPosX; x <= bPosX; x++) {
                            if (points[x][y].getValue() != 0) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            //a 下 右 b
                            return flag;
                        }
                    }
                }
            } else if (bAround[2].getY() < a.getY()) {
                int y = bAround[2].getY();
                if (y < aAround[2].getY())
                    y = aAround[2].getY();
                for (; y < a.getY(); y++) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 上 右 下 b
                        return flag;
                    }
                }
            } else if (aAround[3].getY() > b.getY()) {
                int y = aAround[3].getY();
                if (y > bAround[3].getY())
                    y = bAround[3].getY();
                for (; y > b.getY(); y--) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 下 右 上 b
                        return flag;
                    }
                }
            } else {
                //横线没有
            }
        } else{// if (aPosY > bPosY)
            /**
             * - - - b
             * l # # l
             * a - - -
             */
            boolean flag = true;
            //直接能连
            if (aAround[1].getX() >= b.getX() && bAround[3].getY() >= a.getY()) {
                //a 右 上 b
                return flag;
            } else if (aAround[2].getY() <= b.getY() && bAround[0].getX() <= a.getX()) {
                //a 上 右 b
                return flag;
            } else if (aAround[1].getX() <= b.getX() && bAround[0].getX() >= a.getX()) {
                if (aAround[1].getX() < bAround[0].getX()) {
                    //中间连不上
                } else {
                    for (int x = bAround[0].getX(); x <= aAround[1].getX(); x++) {
                        flag = true;
                        for (int y = aPosY; y <= bPosY; y++) {
                            if (points[x][y].getValue() != 0) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            //a 右 上 右 b
                            return flag;
                        }
                    }
                }
            } else if (bAround[0].getX() < a.getX()) {
                int x = bAround[0].getX();
                if (x < aAround[0].getX())
                    x = aAround[0].getX();
                for (; x < a.getX(); x++) {
                    flag = true;
                    for (int y = bPosY; y <= aPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 左 上 右 b
                        return flag;
                    }
                }
            } else if (aAround[1].getX() > b.getX()) {
                int x = aAround[1].getX();
                if (x > bAround[1].getX())
                    x = bAround[1].getX();
                for (; x > b.getX(); x--) {
                    flag = true;
                    for (int y = bPosY; y <= aPosY; y++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 右 上 左 b
                        return flag;
                    }
                }
            } else {
                //竖线没有
            }

            //检测横线
            if (aAround[2].getY() >= b.getY() && bAround[3].getY() <= a.getY()) {
                if (aAround[2].getY() > bAround[3].getY()) {
                    //中间连不上
                } else {
                    for (int y =aAround[2].getY() ; y <= bAround[3].getY(); y++) {
                        flag = true;
                        for (int x = aPosX; x <= bPosX; x++) {
                            if (points[x][y].getValue() != 0) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            //a 上 右 上 b
                            return flag;
                        }
                    }
                }
            } else if (bAround[3].getY() > a.getY()) {
                int y = bAround[3].getY();
                if (y > aAround[3].getY())
                    y = aAround[3].getY();
                for (; y > a.getY(); y--) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 下 右 上 b
                        return flag;
                    }
                }
            } else if (aAround[2].getY() < b.getY()) {
                int y = aAround[2].getY();
                if (y < bAround[2].getY())
                    y = bAround[2].getY();
                for (; y < b.getY(); y++) {
                    flag = true;
                    for (int x = aPosX; x <= bPosX; x++) {
                        if (points[x][y].getValue() != 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //a 上 右 下 b
                        return flag;
                    }
                }
            } else {
                //横线没有
            }
        }
        return false;
    }

    public static int[][] genMap(int x, int y, int[] nums) {
        if(x*y%2!=0){
            System.out.println("invalid input!");
            return null;
        }
        int[][] res = new int[x+2][y+2];
        //random 生成随机位置和随机数字
        //直接用Collections里的洗牌函数得了
        List<Integer> cache = new ArrayList<Integer>();
        for(int i=0; i<x*y/2; i++){
            int tmp = nums[i%nums.length];
            cache.add(tmp);
            cache.add(tmp);
        }
        System.out.println(cache);
        Collections.shuffle(cache);
        int index=0;
        for(int i=1; i<x+1; i++){
            for(int j=1; j<y+1; j++){
                res[i][j] = cache.get(index);
                index++;
            }
        }

        for(int i=0; i<x+2; i++) {
            System.out.println();
            for (int j = 0; j < y+2; j++) {
                System.out.print(res[i][j]+" ");
            }
        }
        return res;
    }

    public static void main(String []args){
        MatchIt matchIt = new MatchIt();
        int xSize=4;
        int ySize=4;
        int []nums = {1,2,3,9,4};
        Point[][] points = new Point[xSize+2][ySize+2];
        int [][] numArr = matchIt.genMap(xSize, ySize, nums);
        for(int i=0; i < xSize+2; i++) {
            for (int j = 0; j < ySize+2; j++) {
                int value = numArr[i][j];
                points[i][j] = new Point(i,j);
                points[i][j].setValue(value);
            }
        }
        System.out.println();
        for(int i=0; i < xSize+2; i++) {
            System.out.println();
            for (int j = 0; j < ySize+2; j++) {
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

        Position a = new Position(1,1);
        Position b = new Position(3,3);
        points[2][1].setValue(0);
        points[2][2].setValue(0);
        points[2][3].setValue(0);
        points[3][1].setValue(0);

        System.out.println();
        for(int i=0; i < xSize+2; i++) {
            System.out.println();
            for (int j = 0; j < ySize+2; j++) {
                System.out.print(points[i][j].getValue() + " ");
            }
        }
        System.out.println();

        Solution tmp = matchIt.match(points, a, b);
        System.out.println(a+" "+b+" "+tmp);
    }
}
