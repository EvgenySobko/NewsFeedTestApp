package com.test.digitalnomads.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

fun logd(any: Any?) = Log.d("DEBUG", any.toString())

fun Fragment.toast(any: Any?) = Toast.makeText(requireContext(), any.toString(), Toast.LENGTH_SHORT).show()