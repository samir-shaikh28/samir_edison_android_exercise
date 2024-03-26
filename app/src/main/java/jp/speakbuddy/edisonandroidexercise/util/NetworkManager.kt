package jp.speakbuddy.edisonandroidexercise.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import javax.inject.Inject

class NetworkManager @Inject constructor(private val context: Context) {

    private val TAG = "NetworkManager"
    fun isNetworkAvailable(): Boolean {
         return try{
            val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                isNetworkAvailableApi29(cm)
            } else {
                isNetworkAvailable(cm)
            }
        } catch (e: Exception) {
            Log.e(TAG, "isNetworkAvailable: $e")
            false
        }
    }

    private fun isNetworkAvailableApi29(cm: ConnectivityManager?) : Boolean{
        val capability: NetworkCapabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun isNetworkAvailable(cm: ConnectivityManager?): Boolean {
        val networkInfo = cm?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}