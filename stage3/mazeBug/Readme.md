# MazeBug.java
## private Stack<ArrayList<Location>> crossLocation
### 采用了原文件给的栈的结构。栈的操作如下
#### 在move()中
##### 1）并在这个ArrayList后加上next的位置
##### 2）先将栈顶元素提取出来）将上一步所得的新元素放回栈中
##### 3）新建一个只由next的ArrayList并放入栈中

#### 在act()中
#### 1）将栈顶元素排出
#### 2）提取新栈顶元素的第一个元素作为下一个地址

### 用这个结构的好处就是不用在使用last这个元素了，如果需要回溯的时候就直接抛出栈顶再读入新栈顶的第一个元素即可
