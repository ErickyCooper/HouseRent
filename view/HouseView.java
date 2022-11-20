package HouseRent.view;

import HouseRent.domain.House;
import HouseRent.service.HouseService;
import HouseRent.utils.Utility;
import jdk.nashorn.internal.ir.CallNode;

import javax.xml.namespace.QName;

/**
 * 1.显示界面
 * 2.接收用户输入
 * 3.调用HouseService完成对房屋信息的各种操作
 */

public class HouseView {

    private boolean loop = true;        //控制显示菜单
    private char key = ' ';             //接受用户的输入选择
    private HouseService houseservice = new HouseService(4);       //设置数组的大小为10
    private char addChoice = ' ';   //接收用户扩容数据库的选择


    //显示一个主菜单
    public void mainMenu(){
        do {
            System.out.println("=============房屋出租系统菜单=============");
            System.out.println("\t\t\t\t1. 新 增 房 源");
            System.out.println("\t\t\t\t2. 查 找 房 屋");
            System.out.println("\t\t\t\t3. 删 除 房 屋 信 息");
            System.out.println("\t\t\t\t4. 修 改 房 屋 信 息");
            System.out.println("\t\t\t\t5. 房 屋 列 表");
            System.out.println("\t\t\t\t6. 退       出");
            System.out.println("请输入你的选择（1-6）");

            key = Utility.readChar();
            switch (key){
                case '1' :
                    addHouse();
                    break;
                case '2' :
                    findHouse();
                    break;
                case '3' :
                    delHouse();
                    break;
                case '4' :
                    update();
                    break;
                case '5' :
                    listHouses();
                    break;
                case '6' :
                    exit();
                    System.out.println("退出");
                    break;
            }

        }
        while(loop);
    }


    //显示房屋列表
    public void listHouses(){
        System.out.println("=============房屋列表=============");
        System.out.println("编号\t\t房主\t\t\t电话\t\t\t\t地址\t\t\t\t月租\t\t状态（未出租/已出租）");
        House[] houses = houseservice.list();       //得到所有房屋信息
        for(int i = 0 ; i < houses.length ; i++){
            if(houses[i] == null){
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("==========房屋列表显示完毕=============");
    }

    public void addHouse(){

        System.out.println("=============添加房屋=============");
        System.out.print("姓名：");
        String name = Utility.readString(8);
        System.out.print("电话：");
        String phone = Utility.readString(12);
        System.out.print("地址：");
        String address = Utility.readString(16);
        System.out.print("月租：");
        int rent = Utility.readInt();
        System.out.print("状态（未出租/已出租）：");
        String state = Utility.readString(3);
        //创建新的House对象,注意ID是系统分配的，用户不能输入
        House house = new House( 0 , name , phone , address , rent , state);
        if(houseservice.add(house)){
            System.out.println("=============添加房屋成功=============");
        }
        else{
            System.out.println("添加房屋失败,请问是否需要扩容数据库？Y/N");
            addChoice = Utility.readChar();
            if(addChoice == 'Y'){
                houseservice.addDate();
            }
            return;
        }
    }

    public void findHouse(){
        System.out.println("=============查找房屋信息=============");
        System.out.println("=============请输入要查找的ID=============");
        int findID = Utility.readInt();
        House house = houseservice.findHouse(findID);
        if(house != null){
            System.out.println(house);
        }
        else{
            System.out.println("=============查找的房屋不存在=============");
        }
    }

    public void delHouse(){
        System.out.println("=========删除房屋=========");
        System.out.print("请输入待删除房屋的编号（-1退出）");

        int delID = Utility.readInt();
        if(delID == -1){
            System.out.println("===========放弃删除房屋信息===========");
            return;
        }

        char choice = Utility.readConfirmSelection();           //该方法本身就有循环判断的逻辑，必须输出Y/N
        if(choice == 'Y') {                                     //真的删除
            if(houseservice.del(delID)){
                System.out.println("===========删除房屋信息成功===========");
            }
            else {

                System.out.println("===========房屋编号不存在，删除失败===========");
            }
        }
        else{
            System.out.println("===========放弃删除房屋信息===========");
        }
    }


    public void update(){
        System.out.println("=============修改房屋信息=============");
        System.out.println("请选择待修改房屋编号（-1表示退出）");
        int updateID = Utility.readInt();
        if(updateID == -1){
            System.out.println("=============放弃修改房屋信息=============");
            return;
        }

        //返回的是引用类型[即：就是数组的元素]
        //因此后面对 house.setXxx() , 就会修改HouseService 中的houses数组的元素！！！！！！！
        House house = houseservice.findHouse(updateID);
        if(house == null){
            System.out.println("=============修改房屋信息编号不存在=============");
            return;
        }
        System.out.print("姓名(" + house.getName() + "):");
        //如果用户直接回车表示不修改该信息
        String name = Utility.readString(8 , "");
        if(!"".equals(name)){
            house.setName(name);
        }
        System.out.print("电话(" + house.getPhone() + "):");
        String phone = Utility.readString(12 , "");
        if(!"".equals(phone)){
            house.setPhone(phone);
        }
        System.out.print("地址(" + house.getAddress() + "):");
        String address = Utility.readString(18 , "");
        if(!"".equals(address)){
            house.setAddress(address);
        }
        System.out.print("租金(" + house.getRent() + "):");
        int rent = Utility.readInt(-1);
        if(rent != -1){
            house.setRent(rent);
        }
        System.out.print("状态(" + house.getState() + "):");
        String state = Utility.readString(3 , "");
        if(!"".equals(state)){
            house.setState(state);
        }
        System.out.println("=============hqw修改房屋信息成功=============");
    }

    public void exit(){
        char choice = Utility.readConfirmSelection();
        if(choice == 'Y'){
            loop = false;
        }
    }
}
