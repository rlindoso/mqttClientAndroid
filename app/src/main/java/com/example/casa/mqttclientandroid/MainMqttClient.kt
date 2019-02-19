package com.example.casa.mqttclientandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.example.casa.mqttclientandroid.helpers.MQTTHelper
import kotlinx.android.synthetic.main.activity_main_mqtt_client.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage


class MainMqttClient : AppCompatActivity() {
    var dataReceived: TextView? = null
    private lateinit var mqttHelper: MQTTHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mqtt_client)
        mqttHelper = MQTTHelper(applicationContext)
        dataReceived = txtMqtt

        startMqtt()

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                mqttHelper.publish("1")
            } else {
                mqttHelper.publish("0")
            }
        }
    }

    private fun startMqtt() {
        mqttHelper.init()
        mqttHelper.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {

            }

            override fun connectionLost(throwable: Throwable) {

            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Debug", mqttMessage.toString())
                dataReceived!!.text = mqttMessage.toString()
                switch1.isChecked = mqttMessage.toString() == "1"
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {

            }
        })
    }
}
