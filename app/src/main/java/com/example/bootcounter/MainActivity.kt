package com.example.bootcounter

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val notificationsPermissionsUseCase by lazy {
        NotificationsPermissionsUseCase.build(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                refreshUiDependingOnNotificationPermissionState()
            } else {
                refreshUiDependingOnNotificationPermissionState()
            }
        }

    private val statusField by lazy { findViewById<TextView>(R.id.notifications_permission_status) }
    private val requestNotificationsPermissionButton by lazy { findViewById<TextView>(R.id.request_notifications_permission) }
    private val triggerTestNotification by lazy { findViewById<TextView>(R.id.trigger_notification) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestNotificationsPermissionButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        triggerTestNotification.setOnClickListener {
            ShowBootNotificationUseCase.showBootReceivedNotification(this, 0)
        }
    }

    // todo: handle a case when user selected "don't ask again" or denied permission twice
    override fun onResume() {
        super.onResume()
        refreshUiDependingOnNotificationPermissionState()
    }

    private fun refreshUiDependingOnNotificationPermissionState() {
        if (notificationsPermissionsUseCase.isNotificationPermissionGranted(this)) {
            statusField.setText(R.string.okay_to_show_notifications)
            requestNotificationsPermissionButton.isVisible = false
            triggerTestNotification.isVisible = true
        } else if (notificationsPermissionsUseCase.shouldShowRequestPermissionRationale(this)) {
            statusField.setText(R.string.you_need_to_enable_notifications)
            requestNotificationsPermissionButton.isVisible = true
            triggerTestNotification.isVisible = false
        } else {
            statusField.setText(R.string.you_need_to_enable_notifications)
            requestNotificationsPermissionButton.isVisible = true
            triggerTestNotification.isVisible = false
        }
    }
}
