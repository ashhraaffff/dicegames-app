package androidsamples.java.dicegames;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A {@link Fragment} that implements the Game Play screen.
 */
public class GamesFragment extends Fragment {

    private GamesViewModel vm;
    private Button btn_go, btn_info , btn_die1 , btn_die2 , btn_die3 , btn_die4;
    private TextView txt_coins;
    private RadioGroup group_alike;
    private RadioButton rb_2_alike,rb_3_alike,rb_4_alike;
    private EditText edt_wager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);

        btn_go = view.findViewById(R.id.btn_go);
        btn_info = view.findViewById(R.id.btn_info);
        btn_die1 = view.findViewById(R.id.btn_die_1);
        btn_die2 = view.findViewById(R.id.btn_die_2);
        btn_die3 = view.findViewById(R.id.btn_die_3);
        btn_die4 = view.findViewById(R.id.btn_die_4);

        txt_coins = view.findViewById(R.id.txt_coins);

        group_alike = view.findViewById(R.id.radio_group_alike);

        rb_2_alike = view.findViewById(R.id.rb_two_alike);
        rb_3_alike = view.findViewById(R.id.rb_three_alike);
        rb_4_alike = view.findViewById(R.id.rb_four_alike);

        edt_wager = view.findViewById(R.id.et_wager);

        updateCoinsDisplay();

        btn_go.setOnClickListener(v-> onGoClicked());
        btn_info.setOnClickListener(v->{});
        return view;
    }

    private void onGoClicked() {
        String wagerInput = edt_wager.getText().toString();
        if (wagerInput.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a wager amount", Toast.LENGTH_SHORT).show();
            return;
        }

        int wager = Integer.parseInt(wagerInput);
        vm.setWager(wager);

        int selectedRadioId = group_alike.getCheckedRadioButtonId();

        if (selectedRadioId == R.id.rb_two_alike) {
            vm.setGameType(GameType.TWO_ALIKE);
        } else if (selectedRadioId == R.id.rb_three_alike) {
            vm.setGameType(GameType.THREE_ALIKE);
        } else if (selectedRadioId == R.id.rb_four_alike) {
            vm.setGameType(GameType.FOUR_ALIKE);
        } else {
            Toast.makeText(getActivity(), "Please select an alike option", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!vm.isValidWager()) {
            Toast.makeText(getActivity(), "Insufficient balance for this wager", Toast.LENGTH_SHORT).show();
            return;
        }

        GameResult gameResult = vm.play();

        updateCoinsDisplay();
        updateDiceDisplay();

        switch(gameResult) {
            case WIN:
                Toast.makeText(getActivity(), "You won! " + vm.getWager() + " coins", Toast.LENGTH_SHORT).show();
                break;
            case LOSS:
                Toast.makeText(getActivity(), "You lost! " + vm.getWager() + " coins", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void updateCoinsDisplay() {
        txt_coins.setText(String.valueOf(vm.balance));
    }
    private void updateDiceDisplay() {
        Button[] diceButtons = {btn_die1, btn_die2, btn_die3, btn_die4};
        int[] diceValues = vm.diceV;
        for (int i = 0; i < 4; i++) {
            diceButtons[i].setText(String.valueOf(diceValues[i]));
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}