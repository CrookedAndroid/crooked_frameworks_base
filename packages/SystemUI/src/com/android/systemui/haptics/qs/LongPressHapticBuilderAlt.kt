/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.haptics.qs

import android.os.VibrationEffect
import android.util.Log

import com.android.systemui.statusbar.VibratorHelper

import kotlin.math.max

object LongPressHapticBuilderAlt {

    private const val TAG = "LongPressHapticBuilderAlt"
    private const val WARMUP_TIME = 75 /* in ms */
    private const val DAMPING_TIME = 24 /* in ms */

    private val effectDurationAlt = 400
    private val lowTickDurationAlt = 12
    private val spinDurationAlt = 133
    private val vibratorHelper: VibratorHelper? = null

    /** Create the signal that indicates that a long-press action is available. */
    fun createLongPressHintAlt() {
        val nLowTicksAlt = WARMUP_TIME / lowTickDurationAlt
        val rampDownLowTicksAlt = DAMPING_TIME / lowTickDurationAlt

        // Warmup low ticks
        val warmupLowTicks = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        repeat(nLowTicksAlt) {
            vibratorHelper?.vibrate(warmupLowTicks)
        }

        // Spin effect
        val spinEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibratorHelper?.vibrate(spinEffect)

        // Damping low ticks
        val dampingLowTicks = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        repeat(rampDownLowTicksAlt) {
            vibratorHelper?.vibrate(dampingLowTicks)
        }
    }

    /** Create a "snapping" effect that triggers at the end of a long-press gesture */
    fun createSnapEffectAlt() {
        val snapEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibratorHelper?.vibrate(snapEffect)
    }

    /** Creates a signal that indicates the reversal of the long-press animation. */
    fun createReversedEffectAlt(
        pausedProgress: Float,
    ) {
        val durationAlt = pausedProgress * effectDurationAlt
        if (durationAlt == 0f) Log.d(TAG, "durationAlt is 0f")

        val nLowTicksAlt = (durationAlt / lowTickDurationAlt).toInt()
        if (nLowTicksAlt == 0) Log.d(TAG, "nLowTicksAlt is 0")

        val lowTicks = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        repeat(nLowTicksAlt) {
            vibratorHelper?.vibrate(lowTicks)
        }
    }
}
