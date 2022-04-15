package com.brxq.gyminstructor.ui.user.calculators

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentBarbellCalculatorBinding
import kotlin.math.floor

class BarbellCalculatorFragment : Fragment() {

    private lateinit var binding: FragmentBarbellCalculatorBinding


    private val platesArray = mutableListOf<View>()
    private val platesTextArray = mutableListOf<TextView>()

    private val BAR = 20.0
    private val BAR_WITH_COLLARS = 25.0
    private val BAR_MAX_WEIGHT = 480.0
    private val RED_PLATE = 25.0
    private val RED_PLATE_STATUS = true
    private val BLUE_PLATE = 20.0
    private val BLUE_PLATE_STATUS = true
    private val YELLOW_PLATE = 15.0
    private val YELLOW_PLATE_STATUS = true
    private val GREEN_PLATE = 10.0
    private val GREEN_PLATE_STATUS = true
    private val FIVE_PLATE = 5.0
    private val FIVE_PLATE_STATUS = true
    private val TWO_HALF_PLATE = 2.5
    private val TWO_HALF_PLATE_STATUS = true
    private val ONE_QUARTER_PLATE = 1.25
    private val ONE_QUARTER_PLATE_STATUS = true

    private var inputWeight = 0.0
    private var platesToDraw = arrayListOf<Double>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        binding = FragmentBarbellCalculatorBinding.inflate(layoutInflater)

        platesArray.addAll(
            listOf(
                binding.plate0,
                binding.plate1,
                binding.plate2,
                binding.plate3,
                binding.plate4,
                binding.plate5,
                binding.plate6,
                binding.plate7,
                binding.plate8
            )
        )

        platesTextArray.addAll(
            listOf(
                binding.plate0Text,
                binding.plate1Text,
                binding.plate2Text,
                binding.plate3Text,
                binding.plate4Text,
                binding.plate5Text,
                binding.plate6Text,
                binding.plate7Text,
                binding.plate8Text
            )
        )

        binding.barbellCalculate.setOnClickListener {
            val inputField = binding.barbellWeightInput
            if (inputField.text.any()){
                inputWeight = inputField.text.toString().toDouble()
                platesToDraw.clear()
                if (checkIfValid())
                    calculatePlates()
                if (platesToDraw.isNotEmpty())
                    drawPlates()
            }
        }

    }

    private fun drawPlates() {
        val plates = platesArray.subList(0, platesToDraw.size)
        val text = platesTextArray.subList(0, platesToDraw.size)
        for (i: Int in plates.indices) {
            val plateView = plates[i]
            val textView = text[i]
            if (platesToDraw[i].equals(25.0)) {
                drawRed(plateView, textView)
            }
            if (platesToDraw[i].equals(20.0)) {
                drawBlue(plateView, textView)
            }
            if (platesToDraw[i].equals(15.0)) {
                drawYellow(plateView, textView)
            }
            if (platesToDraw[i].equals(10.0)) {
                drawGreen(plateView, textView)
            }
            if (platesToDraw[i].equals(5.0)) {
                drawFive(plateView, textView)
            }
            if (platesToDraw[i].equals(2.5)) {
                drawTwoHalf(plateView, textView)
            }
            if (platesToDraw[i].equals(1.25)) {
                drawOneQuarter(plateView, textView)
            }
        }
        drawCollar(
            platesArray[platesToDraw.size],
            platesTextArray[platesToDraw.size]
        )
    }

    private fun drawRed(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.redPlate))
        textView.text = "25"

    }

    private fun drawBlue(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bluePlate))
        textView.text = "20"

    }

    private fun drawYellow(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellowPlate))
        textView.text = "15"
        var params = plateView.layoutParams
        params.height = 320
        plateView.layoutParams = params

    }

    private fun drawGreen(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greenPlate))
        textView.text = "10"
        var params = plateView.layoutParams
        params.height = 280
        plateView.layoutParams = params

    }

    private fun drawFive(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        textView.text = "5"
        textView.setTextColor(Color.WHITE)
        var params = plateView.layoutParams
        params.height = 220
        plateView.layoutParams = params
        textView.rotation = -90.0f
        textView.textSize = 16F
    }

    private fun drawTwoHalf(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        textView.text = "2.5"
        textView.setTextColor(Color.WHITE)
        var params = plateView.layoutParams
        params.height = 180
        plateView.layoutParams = params
        textView.rotation = -90.0f
        textView.textSize = 16F
    }

    private fun drawOneQuarter(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        textView.text = "1.25"
        textView.setTextColor(Color.WHITE)
        var params = plateView.layoutParams
        params.height = 140
        plateView.layoutParams = params
        textView.rotation = -90.0f
        textView.textSize = 16F
    }

    private fun drawCollar(plateView: View, textView: TextView) {
        plateView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        textView.text = "2.5"
        textView.setTextColor(Color.WHITE)
        val params = plateView.layoutParams
        params.height = 100
        plateView.layoutParams = params
        textView.rotation = -90.0f
        textView.textSize = 16F
    }

    private fun checkIfValid(): Boolean {
        var valueToDivideWith = 0.0
        if (inputWeight.compareTo(BAR_WITH_COLLARS) == -1 || inputWeight.compareTo(BAR_MAX_WEIGHT) == 1) {
            Toast.makeText(
                requireContext(),
                "Incorrect weight. Check and input values",
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        }
        if (ONE_QUARTER_PLATE_STATUS) {
            valueToDivideWith = ONE_QUARTER_PLATE
        }
        if (TWO_HALF_PLATE_STATUS && valueToDivideWith.equals(0.0)) {
            valueToDivideWith = TWO_HALF_PLATE
        }
        if (FIVE_PLATE_STATUS && valueToDivideWith.equals(0.0)) {
            valueToDivideWith = FIVE_PLATE
        }
        if (GREEN_PLATE_STATUS && valueToDivideWith.equals(0.0)) {
            valueToDivideWith = GREEN_PLATE
        }
        if (YELLOW_PLATE_STATUS && valueToDivideWith.equals(0.0)) {
            valueToDivideWith = YELLOW_PLATE
        }
        if (BLUE_PLATE_STATUS && valueToDivideWith.equals(0.0)) {
            valueToDivideWith = BLUE_PLATE
        }
        if (RED_PLATE_STATUS && valueToDivideWith.equals(0.0)) {
            valueToDivideWith = RED_PLATE
        }
        if (valueToDivideWith.equals(0.0)) {
            Toast.makeText(
                requireContext(),
                "No plate available. Check settings!",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        val inputWeightForCheck = inputWeight / valueToDivideWith
        if (inputWeightForCheck.equals(floor(inputWeightForCheck))) {
            return true
        }
        Toast.makeText(
            requireContext(),
            "Incorrect weight, check and try again!",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    //Расчет дисков
    private fun calculatePlates() {
        if (inputWeight.equals(25.0)) {
            drawCollar(binding.plate0, binding.plate0Text)
            return
        }
        inputWeight -= BAR_WITH_COLLARS
        while (inputWeight.compareTo(0.0) == 1) {
            if (RED_PLATE_STATUS && inputWeight >= 2 * RED_PLATE) {
                inputWeight -= 2 * RED_PLATE
                platesToDraw.add(RED_PLATE)
                continue
            }
            if (BLUE_PLATE_STATUS && inputWeight >= 2 * BLUE_PLATE) {
                inputWeight -= 2 * BLUE_PLATE
                platesToDraw.add(BLUE_PLATE)
                continue
            }
            if (YELLOW_PLATE_STATUS && inputWeight >= 2 * YELLOW_PLATE) {
                inputWeight -= 2 * YELLOW_PLATE
                platesToDraw.add(YELLOW_PLATE)
                continue
            }
            if (GREEN_PLATE_STATUS && inputWeight >= 2 * GREEN_PLATE) {
                inputWeight -= 2 * GREEN_PLATE
                platesToDraw.add(GREEN_PLATE)
                continue
            }
            if (FIVE_PLATE_STATUS && inputWeight >= 2 * FIVE_PLATE) {
                inputWeight -= 2 * FIVE_PLATE
                platesToDraw.add(FIVE_PLATE)
                continue
            }
            if (TWO_HALF_PLATE_STATUS && inputWeight >= 2 * TWO_HALF_PLATE) {
                inputWeight -= 2 * TWO_HALF_PLATE
                platesToDraw.add(TWO_HALF_PLATE)
                continue
            }
            if (ONE_QUARTER_PLATE_STATUS && inputWeight >= 2 * ONE_QUARTER_PLATE) {
                inputWeight -= 2 * ONE_QUARTER_PLATE
                platesToDraw.add(ONE_QUARTER_PLATE)
                continue
            }
        }
    }
}
