package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A {@link Fragment} that implements the Wallet screen.
 */
public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";
    private GamesViewModel vm;

    private TextView txtBalance;
    private Button btn_die;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        Log.d(TAG, "VM: " + vm);

        btn_die = view.findViewById(R.id.btn_die);
        txtBalance = view.findViewById(R.id.txt_balance);

        updateUI();

        view.findViewById(R.id.btn_die).setOnClickListener(v -> {
            vm.rollWalletDie();
            Log.d(TAG, "Rolled a "+vm.dieValue());
            updateUI();
        });

        view.findViewById(R.id.btn_games).setOnClickListener(v -> {
            Log.d(TAG, "Going to GamesFragment");
            Navigation.findNavController(view).navigate(R.id.action_walletFragment_to_gamesFragment);
        });

        view.findViewById(R.id.btn_share).setOnClickListener(v -> shareScore());
    }

    private void shareScore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Look! I have " + vm.getBalance() + " coins in my Dice Games Wallet!");
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share your score"));
        }
    }

    /**
     * Updates the UI with the current die value and balance from the ViewModel.
     */
    private void updateUI() {
        btn_die.setText(String.valueOf(vm.dieValue()));
        txtBalance.setText(String.valueOf(vm.getBalance()));
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause in WalletFragment");
        DiceGamesPrefs.setBalance(requireActivity(), vm.getBalance());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume in WalletFragment");
        vm.setBalance(DiceGamesPrefs.balance(requireActivity()));
        txtBalance.setText(String.valueOf(vm.getBalance()));
        updateUI();
    }
}