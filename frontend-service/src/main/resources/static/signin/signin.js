angular.module('market').controller('signinController', function ($scope, $http, $location, $localStorage) {
    const authContextPath = 'http://localhost:5555/auth/';

    $scope.signin = function () {
        $http.post(authContextPath + 'signin', $scope.signinUser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.gainanovMarketUser = {username: $scope.signin.username, token: response.data.token};
                $localStorage.signin = null;
                $location.path('/');
            }
        });
    }
});