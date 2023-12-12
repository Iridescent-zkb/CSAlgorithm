package com.sunshine.chapter01.topic2_1第一个公共节点;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class FindFirstCommonNode {
    public static void main(String[] args) {
        ListNode[] heads = initLinkedList();
        //la 为 1 2 3 4 5 6
        //lb 为 11 22 4 5 6
        ListNode la = heads[0];
        ListNode lb = heads[1];

        int testMethod = 1;
        ListNode node = new ListNode(0);
        switch (testMethod){
            case 1: //方法1：使用hashmap进行遍历查找
                node = findFirstCommonNodeByMap(la,lb);
                break;
            case 2: //方法2：通过集合辅助查找
                node = findFirstCommonNodeBySet(la,lb);
                break;
            case 3: //方法3：通过栈
                node = findFirstCommonNodeByStack(la,lb);
                break;
            case 4: //方法4：通过序列拼接（等值法）
                node = findFirstCommonNodeByCombine(la,lb);
                break;
            case 5: //方法5：通过差值来实现
                node = findFirstCommonNodeBySub(la,lb);
                break;
        }

        System.out.println("公共节点为：" + node.val);
    }
    /*
     * 方法1：使用hashmap进行遍历查找
     * time：O(n) space:O(n)
     */
    public static ListNode findFirstCommonNodeByMap(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null){
            return null;
        }
        ListNode current1 = pHead1;
        ListNode current2 = pHead2;
        HashMap<ListNode,Integer> hashMap = new HashMap<>();
        while (current1 != null){
            hashMap.put(current1,null);
            current1 = current1.next;
        }

        while (current2 != null){
            if (hashMap.containsKey(current2))
                return current2;
            current2  = current2.next;
        }
        return null;
    }

    /*
     * 方法2：通过集合辅助查找
     *  time：O(n) space:O(n)
     */

    public static ListNode findFirstCommonNodeBySet(ListNode headA, ListNode headB){
        Set<ListNode> set = new HashSet<>();
        while (headA != null){
            set.add(headA);
            headA = headA.next;
        }

        while (headB != null){
            if (set.contains(headB))
                return headB;
            headB = headB.next;
        }
        return null;
    }


    /*
     * 方法3：通过栈
     * time：O(n) space:O(n)
     *
     * Stack.peek()
     * peek()函数返回栈顶的元素，但不弹出该栈顶元素。
     * Stack.pop()
     * pop()函数返回栈顶的元素，并且将该栈顶元素出栈。
     *
     */
    public static ListNode findFirstCommonNodeByStack(ListNode headA, ListNode headB){

        Stack<ListNode> stackA = new Stack<>();
        Stack<ListNode> stackB = new Stack<>();

        while (headA != null){
            stackA.push(headA);
            headA = headA.next;
        }

        while (headB != null){
            stackB.push(headB);
            headB = headB.next;
        }

        ListNode preNode = null;
        while (stackA.size() > 0 && stackB.size() > 0){
            if (stackA.peek() == stackB.peek()){
                preNode = stackA.pop();
                stackB.pop();
            }else {
                break;
            }
        }
        return preNode;
    }

    /**
     * 方法4：通过序列拼接（等值法）
     * time：O(n) space:O(1)
     */
    public static ListNode findFirstCommonNodeByCombine(ListNode pHead1, ListNode pHead2){
        if (pHead1 == null || pHead2 == null){
            return null;
        }

        ListNode p1 = pHead1;
        ListNode p2 = pHead2;

        while (p1 != p2){
            p1 = p1.next;
            p2 = p2.next;
            if (p1 != p2){
                //一个链表访问完了就跳到另外一个链表继续访问
                if (p1 == null){
                    p1 = pHead2;
                }
                if (p2 == null){
                    p2 = pHead1;
                }
            }
        }
        return p1;
    }
    /*
       为什么循环体里if(p1!=p2)这个 判断有什么作用：
       简单来说，如果序列不存在交集的时候陷入死循环，例如 list1是1 2 3，list2是4 5 ，
       如果不加判断，list1和list2会不断循环，出不来
       list1: 1  2   3   null 5 null 5  null  5  null  5  null  5
                          4      4       4        4        4
       list2: 4  5 null   2   3 null 2   3  null  2    3  null  2
                     1            1          1             1
   */


    /**
     * 方法5：通过差值来实现
     *  time：O(n) space:O(1)
     * @param pHead1
     * @param pHead2
     * @return
     */

    public static ListNode findFirstCommonNodeBySub(ListNode pHead1, ListNode pHead2){
        if (pHead1 == null || pHead2 == null){
            return null;
        }
        ListNode current1 = pHead1;
        ListNode current2 = pHead2;
        int l1 = 0, l2 = 0;
        //分别统计两个链表的长度
        while (current1 != null){
            current1 = current1.next;
            l1++;
        }

        while (current2 != null){
            current2 = current2.next;
            l2++;
        }
        current1 = pHead1;
        current2 = pHead2;

        int sub = l1 > l2 ? l1 - l2 : l2 - l1;

        //长的先走sub步
        if (l1 > l2){
            int a = 0;
            while (a < sub){
                current1 = current1.next;
                a++;
            }
        }

        //同时遍历两个链表
        while (current2 != current1) {
            current2 = current2.next;
            current1 = current1.next;
        }

        return current1;
    }

    /**
     * 简单构造两个链表
     *
     * @return
     */
    private static ListNode[] initLinkedList(){
        ListNode[] heads = new ListNode[2];
//        构造第一个链表交点之前的元素 1 ->2-> 3
        heads[0] = new ListNode(1);
        ListNode current1 = heads[0];
        current1.next = new ListNode(2);
        current1 = current1.next;
        current1.next = new ListNode(3);
        current1 = current1.next;
//        11->22
//        构造第二个链表交点之前的元素
        heads[1] = new ListNode(11);
        ListNode current2 = heads[1];
        current2.next = new ListNode(22);
        current2 = current2.next;
//        构造公共交点以及之后的元素

        ListNode node4 = new ListNode(4);
        current1.next = node4;
        current2.next = node4;
        ListNode node5 = new ListNode(5);
        node4.next = node5;

        ListNode node6 = new ListNode(6);
        node5.next = node6;

        return heads;
    }


    static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
