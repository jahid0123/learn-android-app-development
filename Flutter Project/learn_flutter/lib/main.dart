import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

main(){
  runApp(const MyApp());
}

class MyApp extends StatelessWidget{

  const MyApp({super.key});


  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      theme: ThemeData(primarySwatch: Colors.green),
      darkTheme: ThemeData(primarySwatch: Colors.amber),
      debugShowCheckedModeBanner: false,
      color: Colors.lightGreen,
      home: HomeActivity(),
    );
  }

}

class HomeActivity extends StatelessWidget{
  const HomeActivity({super.key});


  MySnackBar(message, context){
    
    return ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(message)));
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text('First Flutter App'),
        titleSpacing: 0,
        centerTitle: false,
        backgroundColor: Colors.amber,
        toolbarHeight: 60,
        toolbarOpacity: 1,
        elevation: 4,
        actions: [
          IconButton(onPressed: (){MySnackBar("Comment",context);}, icon: Icon(Icons.comment)),
          IconButton(onPressed: (){MySnackBar("Search",context);}, icon: Icon(Icons.search)),
          IconButton(onPressed: (){MySnackBar("Settings",context);}, icon: Icon(Icons.settings)),
          IconButton(onPressed: (){MySnackBar("Email",context);}, icon: Icon(Icons.email)),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        elevation: 10,
        backgroundColor: Colors.amber ,
        child: Icon(Icons.add),
        onPressed: (){
          MySnackBar('I am floating action button!!', context);
        },
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 2,
          items: [
            BottomNavigationBarItem(icon: Icon(Icons.home), label: "Home"),
            BottomNavigationBarItem(icon: Icon(Icons.contacts), label: "contact"),
            BottomNavigationBarItem(icon: Icon(Icons.person), label: "person"),
          ],
          onTap: (int index){

          if (index == 0){
            MySnackBar("I'm in the home!!", context);
          }
          if (index == 1){
            MySnackBar("I'm in the contact!!", context);
          }
          if (index == 2){
            MySnackBar("I'm in the profile!!", context);
          }

          },
      ),
      drawer: Drawer(
        child: ListView(
          children: [
            DrawerHeader(
              padding: EdgeInsets.all(0),
              
              child: UserAccountsDrawerHeader(
                  accountName: Text("Jahid Ahmed"),
                  accountEmail: Text("ajahid97@gmail.com"),
                  decoration: BoxDecoration(color: Colors.tealAccent),
                  currentAccountPicture: Image.network("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeA9q_GUVxQ_phWQ86qJjVdFQQ5-6pl555_g&s"),
              )),
            ListTile(
              onTap: (){
                MySnackBar("Account Opening", context);
              },
              title: Text("Account Opening"),
              leading: Icon(Icons.home),),
            ListTile(
              onTap: (){
                MySnackBar("Mobile TopUp", context);
              },
              title: Text("Mobile TopUp"),
              leading: Icon(Icons.mobile_friendly),),
            ListTile(onTap: (){
              MySnackBar("Bill Pay", context);
            },
              title: Text("Bill Pay"),
              leading: Icon(Icons.abc_outlined),),
            ListTile(
              onTap: (){
                MySnackBar("Send Money", context);
              },
              title: Text("Send Money"),
              leading: Icon(Icons.send),),
            ListTile(onTap: (){
              MySnackBar("Request Money", context);
            },
              title: Text("Request Money"),
              leading: Icon(Icons.request_quote),),
            ListTile(
              onTap: (){
                MySnackBar("Payment", context);
              },
              title: Text("Payment"),
              leading: Icon(Icons.payment),),
            ListTile(
              onTap: (){
                MySnackBar("Balance Inquiry", context);
              },
              title: Text("Balance Inquiry"),
              leading: Icon(Icons.account_balance),),

          ],
        ),
      ),
      
      body: Center(
        child: Image.network("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeA9q_GUVxQ_phWQ86qJjVdFQQ5-6pl555_g&s"),
      ),

    );
  }

}