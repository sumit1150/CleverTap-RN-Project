/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

 import React from 'react';
 import {
   Text,
   View,Button, Image,Linking,
 } from 'react-native';
 
 import { NavigationContainer } from "@react-navigation/native";
 import { createStackNavigator } from "@react-navigation/stack";
 
 import Home from './Componant/Home';
 
 
 const Main= () =>{
  // const myData="Name:Sumit" <Home data={myData}/>
  const CleverTap = require('clevertap-react-native');
  //const Stack = createNativeStackNavigator();
 
  if(CleverTap!=null){
   
   //Notification channel
   CleverTap.createNotificationChannel("General", "General", "Channel-General", 5, true);
   // The notification channel importance can have any value from 1 to 5. A higher value means a more interruptive notification.
   //Setting up the clevertap debugging
   CleverTap.setDebugLevel(3);
   //console.log("Clevertap", CleverTap.getCleverTapID());
 
   CleverTap.addListener(CleverTap.CleverTapPushNotificationClicked, (e)=>{
     CleverTap.recordEvent('Custom_Notification_Clicked_event');
     console.log("e value",e);
     // "wzrk_dl": "ctdl://ct.com/deep"
   });
 
 //Deeplink implimentation start
 
 function _handleOpenUrl(event, from)
  {
   console.log('handleOpenUrl', event.url, from);
   if(event.url=='ctdl://ct.com/deep'){
   //  navigation.navigate('Home', { name: 'Jane' })
   }else{
     console.log('Deeplink failed')
   }
   
  }
 
  // Listener to handle incoming deep links
  Linking.addEventListener('url', _handleOpenUrl);
 
  /// this handles the case where a deep link launches the application
  Linking.getInitialURL().then((url) => {
      if (url) {
          console.log('launch url', url);
          _handleOpenUrl({ url });
      }
  }).catch(err => console.error('launch url error', err));
 
  // check to see if CleverTap has a launch deep link
  // handles the case where the app is launched from a push notification containing a deep link
  CleverTap.getInitialUrl((err, url) => {
      if (url) {
          console.log('CleverTap launch url', url);
          _handleOpenUrl({ url }, 'CleverTap');
      } else if (err) {
          console.log('CleverTap launch url', err);
      }
  });
 
  //End
 
 }
  else{
   console.log("found null value");
 }
    return (
 
   
 
     <View style={{flex:1, backgroundColor:'skyblue'}}>
 
       <View style={{flex:1,height:190}}>
       <Image style={{width:360,height:180}} source={require('./custom_res/c.png')} />
       <Text style={{textAlign:'center' }}> CleverTap React Native SDK</Text>
       </View>
       <View style={{flex:2.8,backgroundColor:'steelblue', marginTop:10}}>
       <Button  onPress={() => {CleverTap.recordEvent('React_Sumit_Event');}} title="Press to Raise Custom Event"/>
       </View>
       
     </View>
   
   )
 };
 
 /*const appNavigator=createStackNavigator({
   Home:{
     screen:App,
   }
 })*/
 
 export default Main;
 //export default createAppContainer(appNavigator);
 