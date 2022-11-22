package com.example.myapplication.presentation.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.model.User;
import com.example.myapplication.data.remote.dto.UserDTO;
import com.example.myapplication.data.responsitory.AuthenticationRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<AppResource<User>> userResource = new MutableLiveData<>();
    private AuthenticationRepository repository;

    public RegisterViewModel(Context context){
        repository = new AuthenticationRepository(context);
    }

    public LiveData<AppResource<User>> getUserResource(){
        return userResource;
    }

    public void register(String email, String password, String name, String phone, String address){
        userResource.setValue(new AppResource.Loading(null));
        Call<AppResource<UserDTO>> callRegister = repository.register(email, password, name, phone, address);

        callRegister.enqueue(new Callback<AppResource<UserDTO>>() {
            @Override
            public void onResponse(Call<AppResource<UserDTO>> call, Response<AppResource<UserDTO>> response) {
                if (response.isSuccessful()){
                    AppResource<UserDTO> userDTOAppResource = response.body();
                    UserDTO userDTO = userDTOAppResource.data;
                    User user = new User(userDTO.getEmail(),
                            userDTO.getName(),
                            userDTO.getPhone(),
                            userDTO.getToken());
                    userResource.setValue(new AppResource.Success<>(user));
                }else{
                    Log.d("BBB", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AppResource<UserDTO>> call, Throwable t) {
                userResource.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
