require 'json'
require 'models/payment'

class ApiController < ApplicationController
  before do
    content_type :json
  end

  get '/api' do
    JSON.dump('message' => 'Hello Developer')
  end

  get '/api/bookings' do
    bookings = Payment.all.map(&:sanitized_attributes)
    JSON.dump('bookings' => bookings)
  end
end
