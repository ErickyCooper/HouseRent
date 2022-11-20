package HouseRent.service;

import HouseRent.domain.House;
import HouseRent.utils.Utility;

/**
 * 1.响应HouseView的调用
 * 2.完成对房屋信息的各种操作（增删改查  crud）
 * 3.定义House[]，保存House对象
 */

public class HouseService {

    private House[] houses;         //存放House对象
    private int houseNum = 0;       //记录当前有多少个房屋信息
    private int idCounter = 0;      //记录当前的ID自增长到哪个值


    public HouseService(int size){
        houses = new House[size];       //当创建HouseService对象，指定数组大小
    }

    //list方法，返回houses
    public House[] list(){
        return houses;
    }

    //add方法，添加新对象，返回Boolean
    public boolean add(House newHouse){
        if (houseNum == houses.length){
            System.out.println("数组已满，不能再添加");
            return false;
        }


        //把newHouse对象加入到,新增加了一个房屋
        houses[houseNum++] = newHouse;
        //需要设计ID自增长的机制，然后更新newHouse的ID
            newHouse.setId(++idCounter);
        return true;
    }

    public void addDate(){
        if(houses.length - 1 != 0){
            House[] newhouses = new House[houses.length * 2];
            change(houses , newhouses);
            houses = newhouses;
        }
    }

    public void change(House[] a , House[] b){
        for( int i = 0 ; i < a.length ; i++){
            b[i] = a[i];
        }
    }

    //删除房屋信息
    public boolean del(int delID){

        //先找到删除的房屋信息对应的下标
        //一定要搞清楚 下标和房屋编号不是一回事

        int index = -1;
        for(int i = 0 ; i < houseNum ; i++){
            if(delID == houses[i].getId()){         //要删除的房屋对应的ID，是数组下标为i的元素
                index = i;                          //使用index记录i
            }
        }

        if(index == -1){                            //说明delID在数组中不存在
            return false;
        }

        for(int i = index ; i < houseNum - 1 ; i++){
            houses[i] = houses[i+1];
            if(idCounter >= i){
                houses[i].setId(i + 1);
            }
        }
        houses[--houseNum] = null;
        idCounter = houseNum;
        return true;
    }

    public House findHouse(int findID){
        for( int i = 0 ; i < houseNum ; i++){
            if(findID == houses[i].getId()){
                return houses[i];
            }
        }


        return null;
    }
}
