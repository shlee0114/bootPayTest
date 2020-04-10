package com.example.bootpaytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import androidx.fragment.*
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.listener.CancelListener
import kr.co.bootpay.listener.ConfirmListener
import kr.co.bootpay.listener.DoneListener
import kr.co.bootpay.listener.ReadyListener

class MainActivity : AppCompatActivity() {

    private val APPLICATION_ID = "5e8bed5b02f57e0022d63de7"
    private val stuck = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BootpayAnalytics.init(this, APPLICATION_ID)

        button.setOnClickListener {
            val bootUser = BootUser().setPhone("010-3422-4264")
            val bootExtra = BootExtra().setQuotas(intArrayOf(1,2,3))

            Bootpay.init(this)
                    .setApplicationId(APPLICATION_ID)
                    .setPG(PG.KCP) //결제할 PG사
                    .setMethod(Method.CARD) //결제수단
                    .setContext(this)
                    .setBootUser(bootUser) 
                    .setBootExtra(bootExtra)
                    .setUX(UX.PG_DIALOG)
                    .setName("나의 사랑") //결제할 상품명
                    .setOrderId("1234") //결제 고유 id
                    .setPrice(1000) //결제 금액
                    .addItem("하트 뿅뿅", 1, "ITEM_CODE_HEART", 100) //주문정보에 담길 상품정보, 통계를 위해서 사용
                    .onConfirm{
                        if(0 < stuck) Bootpay.confirm(it)
                        else Bootpay.removePaymentWindow()
                        Log.d("confirm", it)
                    }
                    .onDone{
                        Log.d("done", it)
                    }
                    .onReady {
                        Log.d("ready", it)
                    }
                    .onCancel {
                        Log.d("cancle", it)
                    }
                    .onError {
                        Log.d("error", it)
                    }
                    .onClose {
                        Log.d("close", it)
                    }
                    .request()
        }
    }
}
